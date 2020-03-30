#include <sqlite3.h>
#include <stdlib.h> 
#include <string.h>
#include <stdio.h> 

#include "getroadinfo.h"
#include "geometry.h"

bool road_open(const char* file, road_handle* handle)
{
	// 
	if(handle == NULL)
		return false;
	sqlite3* db = NULL;
	int nRes = sqlite3_open(file, &db);
	if(nRes != SQLITE_OK)
	{
		return false;
	}
	handle->db = (void*)db;
	handle->road_name[0] = '\0'; 
	handle->region = NULL;
	handle->number = 0;
	return true;
}

bool get_roadinfo(road_handle* handle, double gps_long, double gps_lat, road_info* info)
{
	// �ж�region�Ƿ����,ֱ���жϵ�ǰregion
	double roadwidth = 3.5;
	if(handle == NULL)
		return false;
	Point2f ptNow;
	ptNow.x = gps_long;
	ptNow.y = gps_lat;
	if(handle->region != NULL)
	{
		// 	
		Point2f* pData = (Point2f*)handle->region;
		if(inside_polygon(pData, handle->number, ptNow) == true)
		{
			double dis = get_dis_ptLine(ptNow, pData[0], pData[1]);
			strcpy(info->road_name, handle->road_name);
			info->road_dist = dis;
			info->road_line = (int)(dis/roadwidth) + 1;
			return true;
		}
		else
		{
			handle->road_name[0] = '\0'; 
			handle->number = 0;
			free(handle->region);
			handle->region = NULL;
		}
	}
	// �����ݿ��ѯ
	sqlite3* db = (sqlite3* )handle->db;
	sqlite3_stmt* stmt = NULL;
	char buffer[1024] = "\0";
	sprintf(buffer, "select road_name,rgn_data from road_rect where lon_min <= %.8f and lon_max >= %.8f and lat_min <= %.8f and lat_max >= %.8f", 
		gps_long, gps_long, gps_lat, gps_lat);
	sqlite3_prepare(db, buffer, -1, &stmt, 0);
	while(sqlite3_step(stmt) == SQLITE_ROW)
	{

		// ����
		char* data= (char *)sqlite3_column_blob(stmt, 1);
		int len = sqlite3_column_bytes(stmt, 1);
		int pointNum = len/8;
		Point2f* pData = (Point2f*)malloc(sizeof(Point2f) * pointNum);
		for(int i = 0; i < pointNum; i++)
		{
			pData[i].x = (double(*((int*)data + 2*i)))/10000000;
			pData[i].y = (double(*((int*)data + 2*i+1)))/10000000;
		}
		// ������������
		if(inside_polygon((Point2f*)pData, pointNum, ptNow) == true)
		{
			double dis = get_dis_ptLine(ptNow, pData[0], pData[1]);
			const char* road_name = (const char*)sqlite3_column_text(stmt, 0);
			strcpy(info->road_name, road_name);
			info->road_dist = dis;
			info->road_line = (int)(dis/roadwidth) + 1;
			// ��ֵ��handle
			handle->number = pointNum;
			handle->region = pData;
			strcpy(handle->road_name, info->road_name);
			sqlite3_finalize(stmt);
			return true;
		}
		free(pData);
	};	   
	sqlite3_finalize(stmt);
	return false;	   
}

void road_close(road_handle* handle)
{
	if(handle->db != NULL)
	{
		sqlite3_close((sqlite3*)handle->db);
		handle->db = NULL;
	}
	if(handle->region != NULL)
	{
		free(handle->region);
		handle->region = NULL;
	}
	handle->road_name[0] = '\0'; 
	handle->number = 0;
}