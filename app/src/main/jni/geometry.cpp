#include <math.h>
#include "geometry.h"

// east, north单位为米
void GpsTranXy(double lonNow, double latNow, double lonBase, double latBase, double& east, double& north)
{
	// 纬度转为距离米的系数
	double dBToM = 111193;
	// 经度转为距离米的系数
	double dLToM = dBToM * cos(latNow / 57.3);
	east = (lonNow - lonBase) * dLToM;
	north = (latNow - latBase) * dBToM;
}

// 计算点到直线的距离,左为正，右为负
double get_dis_ptLine(Point2f pt0, Point2f pt1, Point2f pt2)
{
	double x0, x1, x2, y0, y1, y2;
	x1 = y1 = 0;
	GpsTranXy(pt2.x, pt2.y, pt1.x, pt1.y, x2, y2);
	GpsTranXy(pt0.x, pt0.y, pt1.x, pt1.y, x0, y0);
	double d = ((y1 - y2) * x0 + (x2 - x1) * y0 + x1 * y2 - x2 * y1) / sqrt(pow(y2 - y1, 2) + pow(x2 - x1, 2));
	return d;
}

#define MIN(x,y) ((x)<(y)?(x):(y))
#define MAX(x,y) ((x)<(y)?(y):(x))
bool inside_polygon(Point2f *polygon, int N, Point2f p)
{
	int counter = 0;
	int i;
	double xinters;
	Point2f p1, p2;

	p1 = polygon[0];
	for (i = 1; i <= N; i++)
	{
		p2 = polygon[i % N];
		if (p.y > MIN(p1.y, p2.y))
		{
			//低
			if (p.y <= MAX(p1.y, p2.y))
			{
				//高
				if (p.x <= MAX(p1.x, p2.x))
				{
					//右
					if (p1.y != p2.y)
					{
						//简单忽略平行X轴这种情况
						xinters = (p.y - p1.y)*(p2.x - p1.x) / (p2.y - p1.y) + p1.x;//交叉点坐标 参考./media/point-and-polygon/xinters.jpg
						if (p1.x == p2.x || p.x <= xinters)
							counter++;
					}
				}
			}
		}
		p1 = p2;
	}

	if (counter % 2 == 0)
		return false;
	else
		return true;
}

int test () {
    return 99;
}