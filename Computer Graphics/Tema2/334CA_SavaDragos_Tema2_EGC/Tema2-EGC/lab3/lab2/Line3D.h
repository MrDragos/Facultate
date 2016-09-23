#pragma once
//#include <vector>
//#include "Framework/Point2D.h"
#include "Framework/Object3D.h"

using namespace std;

class Line3D:public Object3D{

public:

	//a = punctul de start
	//b = punctul de sfarsit
	//thickness = grosimea liniei
	Line3D(Point3D *a,Point3D *b,float thickness,Color _color){

		vertices.push_back(new Point3D(a->x, a->y, a->z));
		vertices.push_back(new Point3D(b->x,b->y , b->z));
		vertices.push_back(new Point3D(b->x+thickness, b->y, b->z));
		vertices.push_back(new Point3D(a->x+thickness, a->y, a->z));

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

		fill = true;

	}

	

	~Line3D(){

	}

};