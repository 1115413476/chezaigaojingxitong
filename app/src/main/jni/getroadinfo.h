#ifdef __cplusplus
extern "C" {
#endif

struct road_handle
{
	void* db;
	char road_name[10];
	void* region;
	int  number;
};

struct road_info
{
	char road_name[10];
	int road_line;
	double road_dist;
};



bool road_open(const char* file, road_handle* handle);

bool get_roadinfo(road_handle* handle, double gps_long, double gps_lat, road_info* info);

void road_close(road_handle* handle);


#ifdef __cplusplus
}
#endif