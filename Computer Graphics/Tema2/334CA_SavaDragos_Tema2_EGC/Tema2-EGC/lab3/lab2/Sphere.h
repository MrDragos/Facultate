#pragma once
//#include <vector>
//#include "Framework/Point2D.h"
#include "Framework/Object3D.h"


#define PI 3.14159265358979323846
using namespace std;

class Sphere:public Object3D{

public:

	//Point3D* center;
	

	Sphere(float radius, Point3D* center){

		//this->center = center;

		vector	<int>	contour;
		float d = 18;
		float theta;
		float phi;//mother of PHI
		int i = 0;

		for (phi = 0; phi < 2 * PI; phi += PI / d) {
			// AICI INCEPE
			contour.clear();
			for (theta = 0; theta <= PI; theta += PI / d) {
				Point3D *p = new Point3D();
				//p->x = center->x + radius * cos(phi) * sin(theta);
				//p->y = center->y + radius * sin(phi) * sin(theta);
				//p->z = center->z + radius * cos(theta);
				p->x = center->x + radius * cos(phi) * sin(theta);
				p->y = center->y + radius * sin(phi) * sin(theta);
				p->z = center->z + radius * cos(theta);
				vertices.push_back(p);
				//transf_vertices.push_back(p);
				contour.push_back(i);
				i++;
			}

			faces.push_back(new Face(contour));
		}

		fill = false;
		color.r = color.b = color.r = 0;
	}

	Sphere(float radiu,Point3D *cente,Color _color,bool _fill){

		vector	<int>	contour;
		//int phi;//mother of PHI
		float phi;
		//vector	<int>	contour;
		//int d = 9;
		//int theta;
		float d = 18;
		float theta;
		//vector <Face*> faces;
		//vector <Point3D*> vertices;
		int i = 0;

		float n = 100;

		Point3D* center = new Point3D(640, 100, -300);


		float radius = n / 2;
		for (phi = 0; phi < 2 * PI; phi += PI / d) {
			// AICI INCEPE
			contour.clear();
			for (theta = 0; theta <= PI; theta += PI / d) {
				Point3D *p = new Point3D();
				p->x = center->x + radius * cos(phi) * sin(theta);
				p->y = center->y + radius * sin(phi) * sin(theta);
				p->z = center->z + radius * cos(theta);
				//p->x = n + radius * cos(phi) * sin(theta);
				//p->y = 2 * n + radius * sin(phi) * sin(theta);
				//p->z = n + radius * cos(theta);
				vertices.push_back(p);
				//transf_vertices.push_back(p);
				contour.push_back(i);
				i++;
			}
			faces.push_back(new Face(contour));
			// AICI SE TERMINA
		}

		fill = _fill;

		color.r = _color.r;
		color.g = _color.g;
		color.b = _color.b;
	}

	~Sphere(){

	}


};