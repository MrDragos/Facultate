#pragma once
//#include <vector>
//#include "Framework/Point2D.h"
#include "Framework/Object3D.h"

using namespace std;

class Rectangle3D:public Object3D{

public:

	Point3D *center;

	Rectangle3D(Point3D *startPoint,float width,float height){

		float z = 0;

		vertices.push_back(new Point3D(startPoint->x,0, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x+width, 0, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x+width, 0, startPoint->z+height));
		vertices.push_back(new Point3D(startPoint->x, 0, startPoint->z+height));

		for (int i = 0; i < vertices.size(); i++){
			transf_vertices.push_back(vertices[i]);
		}

		vector <int> contour;

		contour.clear();
		contour.push_back(0);
		contour.push_back(1);
		contour.push_back(2);
		contour.push_back(3);


		faces.push_back(new Face(contour));

		color.r = 0;
		color.g = 0;
		color.b = 0;

		fill = false;

	}

	Rectangle3D(Point3D *startPoint, float width, float height,Color _color,bool _fill){

		float z = 0;

		vertices.push_back(new Point3D(startPoint->x, 0, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + width, 0, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + width, 0, startPoint->z + height));
		vertices.push_back(new Point3D(startPoint->x, 0, startPoint->z + height));

		for (int i = 0; i < vertices.size(); i++){
			transf_vertices.push_back(vertices[i]);
		}

		vector <int> contour;

		contour.clear();
		contour.push_back(0);
		contour.push_back(1);
		contour.push_back(2);
		contour.push_back(3);


		faces.push_back(new Face(contour));

		color.r = _color.r;
		color.g = _color.g;
		color.b = _color.b;

		fill = _fill;
	}

	~Rectangle3D(){

	}
};