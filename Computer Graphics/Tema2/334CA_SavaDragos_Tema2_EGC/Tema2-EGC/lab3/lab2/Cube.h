#pragma once
//#include <vector>
//#include "Framework/Point2D.h"
#include "Framework/Object3D.h"

using namespace std;

class Cube:public Object3D{

public:

	//vector <Point3D*> vertices;
	//vector <Face*> faces;
	

	float size;	//lungimea cubului
	Point3D* center;
	Point3D* startPoint;

	//n este lungimea cubului
	//center
	//start
	Cube(float n, Point3D *startPoint){

		this->startPoint = startPoint;
		this->size = n;

		computeCenter();
		/*
		vertices.push_back(new Point3D(0, 0, 0));
		vertices.push_back(new Point3D(n, 0, 0));
		vertices.push_back(new Point3D(n, 0, n));
		vertices.push_back(new Point3D(0, 0, n));
		//varfurile de sus
		vertices.push_back(new Point3D(0, n, 0));
		vertices.push_back(new Point3D(n, n, 0));
		vertices.push_back(new Point3D(n, n, n));
		vertices.push_back(new Point3D(0, n, n));
		*/

		
		vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + n, startPoint->y, startPoint->z));
		vertices.push_back(new Point3D(n + startPoint->x, startPoint->y, startPoint->z - n));
		vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z - n));
		//varfurile de sus
		vertices.push_back(new Point3D(startPoint->x, startPoint->y + n, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + n, startPoint->y + n, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + n, startPoint->y + n, startPoint->z - n));
		vertices.push_back(new Point3D(startPoint->x, startPoint->y + n, startPoint->z - n));
		
		/*
		vertices.push_back(new Point3D(center->x, center->y, center->z));
		vertices.push_back(new Point3D(center->x + n, center->y, center->z));
		vertices.push_back(new Point3D(n + center->x, center->y, center->z + n));
		vertices.push_back(new Point3D(center->x, center->y, center->z + n));
		//varfurile de sus
		vertices.push_back(new Point3D(center->x, center->y + n, center->z));
		vertices.push_back(new Point3D(center->x + n, center->y + n, center->z));
		vertices.push_back(new Point3D(center->x + n, center->y + n, center->z + n));
		vertices.push_back(new Point3D(center->x, center->y + n, center->z + n));
		*/
		for (int i = 0; i < vertices.size(); i++){
			transf_vertices.push_back(vertices[i]);
		}

		vector <int> contour;

		//cele 6 fete

		//fata jos
		contour.clear();
		contour.push_back(0);
		contour.push_back(1);
		contour.push_back(2);
		contour.push_back(3);
		faces.push_back(new Face(contour));
		//fata sus
		contour.clear();
		contour.push_back(4);
		contour.push_back(5);
		contour.push_back(6);
		contour.push_back(7);
		faces.push_back(new Face(contour));
		//fata fata
		contour.clear();
		contour.push_back(0);
		contour.push_back(1);
		contour.push_back(5);
		contour.push_back(4);
		faces.push_back(new Face(contour));
		//fata dreapta
		contour.clear();
		contour.push_back(1);
		contour.push_back(2);
		contour.push_back(6);
		contour.push_back(5);
		faces.push_back(new Face(contour));
		//fata spate
		contour.clear();
		contour.push_back(2);
		contour.push_back(3);
		contour.push_back(7);
		contour.push_back(6);
		faces.push_back(new Face(contour));
		//fata stanga
		contour.clear();
		contour.push_back(3);
		contour.push_back(0);
		contour.push_back(4);
		contour.push_back(7);
		faces.push_back(new Face(contour));

		//int i;
		//for (i = 0; i < listV.size(); i++)
		//{
			//vertices.push_back(new Point3D(listV[i]->x, listV[i]->y, listV[i]->z));
			//transf_vertices.push_back(new Point3D(listV[i]->x, listV[i]->y, listV[i]->z));
		//}
		//for (i = 0; i < listF.size(); i++)
		//	faces.push_back(new Face(listF[i]->contour));

		color.r = 0;
		color.g = 0;
		color.b = 0;

		fill = false;

	}

	//calculeaza centrul cubului


	void computeCenter(){
		center = new Point3D();

		center->x = startPoint->x + size / 2;
		center->y = startPoint->y + size / 2;
		center->z = startPoint->z - size / 2;
	}

	Cube(float n, Point3D *startPoint, Color _color, bool _fill){

		this->startPoint = startPoint;
		this->size = n;

		computeCenter();

		/*
		vertices.push_back(new Point3D(0, 0, 0));
		vertices.push_back(new Point3D(n, 0, 0));
		vertices.push_back(new Point3D(n, 0, n));
		vertices.push_back(new Point3D(0, 0, n));
		//varfurile de sus
		vertices.push_back(new Point3D(0, n, 0));
		vertices.push_back(new Point3D(n, n, 0));
		vertices.push_back(new Point3D(n, n, n));
		vertices.push_back(new Point3D(0, n, n));

		*/

		//vertices.push_back(new Point3D(center->x, center->y, center->z));
		//vertices.push_back(new Point3D(n, center->y, center->z));
		//vertices.push_back(new Point3D(n, center->y, n));
		//vertices.push_back(new Point3D(center->x, center->y, n));
		//varfurile de sus
		//vertices.push_back(new Point3D(center->x, n, center->z));
		//vertices.push_back(new Point3D(n, n, center->z));
		//vertices.push_back(new Point3D(n, n, n));
		//vertices.push_back(new Point3D(center->x, n, n));

		vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + n, startPoint->y, startPoint->z));
		vertices.push_back(new Point3D(n + startPoint->x, startPoint->y, startPoint->z - n));
		vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z - n));
		//varfurile de sus
		vertices.push_back(new Point3D(startPoint->x, startPoint->y + n, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + n, startPoint->y + n, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + n, startPoint->y + n, startPoint->z - n));
		vertices.push_back(new Point3D(startPoint->x, startPoint->y + n, startPoint->z - n));

		for (int i = 0; i < vertices.size(); i++){
			transf_vertices.push_back(vertices[i]);
		}

		vector <int> contour;

		//cele 6 fete

		//fata jos
		contour.clear();
		contour.push_back(0);
		contour.push_back(1);
		contour.push_back(2);
		contour.push_back(3);
		faces.push_back(new Face(contour));
		//fata sus
		contour.clear();
		contour.push_back(4);
		contour.push_back(5);
		contour.push_back(6);
		contour.push_back(7);
		faces.push_back(new Face(contour));
		//fata fata
		contour.clear();
		contour.push_back(0);
		contour.push_back(1);
		contour.push_back(5);
		contour.push_back(4);
		faces.push_back(new Face(contour));
		//fata dreapta
		contour.clear();
		contour.push_back(1);
		contour.push_back(2);
		contour.push_back(6);
		contour.push_back(5);
		faces.push_back(new Face(contour));
		//fata spate
		contour.clear();
		contour.push_back(2);
		contour.push_back(3);
		contour.push_back(7);
		contour.push_back(6);
		faces.push_back(new Face(contour));
		//fata stanga
		contour.clear();
		contour.push_back(3);
		contour.push_back(0);
		contour.push_back(4);
		contour.push_back(7);
		faces.push_back(new Face(contour));

		//int i;
		//for (i = 0; i < listV.size(); i++)
		//{
		//vertices.push_back(new Point3D(listV[i]->x, listV[i]->y, listV[i]->z));
		//transf_vertices.push_back(new Point3D(listV[i]->x, listV[i]->y, listV[i]->z));
		//}
		//for (i = 0; i < listF.size(); i++)
		//	faces.push_back(new Face(listF[i]->contour));

		color.r = _color.r;
		color.g = _color.g;
		color.b = _color.b;

		fill = _fill;

	}


	~Cube()
	{}


};