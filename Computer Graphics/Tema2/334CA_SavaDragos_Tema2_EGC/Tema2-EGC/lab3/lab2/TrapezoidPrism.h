#pragma once
//#include <vector>
//#include "Framework/Point2D.h"
#include "Framework/Object3D.h"

using namespace std;

class TrapezoidPrism:public Object3D{

public:

	Point3D* startPoint;
	Point3D* center;

	//L = lungime baza mare
	//l = lungime baza mica
	//W = latime baza mare
	//w = latime baza mica
	//height = inaltime orisma
	//pentru w = W e trapezoid 3D
	//altfel e trunchi de piramida cu baza patrat

	TrapezoidPrism(Point3D* startPoint,float L,float l,float W,float w,float height){

		this->startPoint = startPoint;

		//varfurile de sus
		vertices.push_back(new Point3D(startPoint->x + (L - l) / 2, startPoint->y + height, startPoint->z - (W - w) / 2));
		vertices.push_back(new Point3D(startPoint->x + (L + l) / 2, startPoint->y + height, startPoint->z - (W - w) / 2));
		vertices.push_back(new Point3D(startPoint->x + (L + l) / 2, startPoint->y + height, startPoint->z - (W + w) / 2));
		vertices.push_back(new Point3D(startPoint->x + (L - l) / 2, startPoint->y + height, startPoint->z - (W + w) / 2));

		vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + L, startPoint->y, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + L, startPoint->y, startPoint->z - W));
		vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z - W));


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

		color.r = 0;
		color.g = 0;
		color.b = 0;

		fill = false;



	}

	//L = lungime baza mare
	//l = lungime baza mica
	//W = latime baza mare
	//w = latime baza mica
	//height = inaltime orisma
	//pentru w = W e trapezoid 3D
	//altfel e trunchi de piramida cu baza patrat

	TrapezoidPrism(Point3D* startPoint, float L, float l, float W, float w, float height, Color _color, bool _fill){

		this->startPoint = startPoint;


		//varfurile de sus
		vertices.push_back(new Point3D(startPoint->x + (L - l) / 2, startPoint->y + height, startPoint->z - (W - w) / 2));
		vertices.push_back(new Point3D(startPoint->x + (L + l) / 2, startPoint->y + height, startPoint->z - (W - w) / 2));
		vertices.push_back(new Point3D(startPoint->x + (L + l) / 2, startPoint->y + height, startPoint->z - (W + w) / 2));
		vertices.push_back(new Point3D(startPoint->x + (L - l) / 2, startPoint->y + height, startPoint->z - (W + w) / 2));

		vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + L, startPoint->y, startPoint->z));
		vertices.push_back(new Point3D(startPoint->x + L, startPoint->y, startPoint->z - W));
		vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z - W));

		for (int i = 0; i < vertices.size(); i++){
			transf_vertices.push_back(vertices[i]);
		}

		vector <int> contour;

		//cele 6 fete


		//fata sus
		contour.clear();
		contour.push_back(4);
		contour.push_back(5);
		contour.push_back(6);
		contour.push_back(7);
		faces.push_back(new Face(contour));

		//fata jos
		contour.clear();
		contour.push_back(0);
		contour.push_back(1);
		contour.push_back(2);
		contour.push_back(3);
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


		color.r = _color.r;
		color.g = _color.g;
		color.b = _color.b;

		fill = _fill;


	}


	~TrapezoidPrism(){

	}


};