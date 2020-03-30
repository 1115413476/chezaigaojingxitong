//定义Point2f结构体
struct Point2f
{
	double x;
	double y;
};

// 计算点到直线的距离,左为正，右为负
double get_dis_ptLine(Point2f pt0, Point2f pt1, Point2f pt2);

bool inside_polygon(Point2f *polygon, int N, Point2f p);

int test ();