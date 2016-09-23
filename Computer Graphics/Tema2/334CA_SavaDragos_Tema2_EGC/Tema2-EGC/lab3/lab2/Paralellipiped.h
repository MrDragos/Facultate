#pragma once
//#include <vector>
//#include "Framework/Point2D.h"
#include "Framework/Object3D.h"

using namespace std;

class Paralellipiped :public Object3D{

public:

	float L;	//lungimea paralelipipedului
	float l;	//latimea
	float h;	//inaltimea

	Point3D* center;
	Point3D* startPoint;	//coltul stanga jos al fetei din fata

	Paralellipiped(Point3D* startPoint,float L,float h,float l){

		this->startPoint = startPoint;
		this->L = L;
		this->l = l;
		this->h = h;
		
		computeCenter();
		/*

		vertices.push_back(new Point3D(0, 0, 0));
		vertices.push_back(new Point3D(L, 0, 0));
		vertices.push_back(new Point3D(L, 0, l));
		vertices.push_back(new Point3D(0, 0, l));
		//varfurile de sus
		vertices.push_back(new Point3D(0, h, 0));
		vertices.push_back(new Point3D(L, h, 0));
		vertices.push_back(new Point3D(L, h, l));
		vertices.push_back(new Point3D(0, h, l));

		*/

		float x = startPoint->x;
		float y = startPoint->y;
		float z = startPoint->z;

		vertices.push_back(new Point3D(x, y, z));
		vertices.push_back(new Point3D(L, y, z));
		vertices.push_back(new Point3D(L, y, l));
		vertices.push_back(new Point3D(x, y, l));
		//varfurile de sus
		vertices.push_back(new Point3D(x, h, z));
		vertices.push_back(new Point3D(L, h, z));
		vertices.push_back(new Point3D(L, h, l));
		vertices.push_back(new Point3D(x, h, l));


		for (int i = 0; i < vertices.size(); i++){
			transf_vertices.push_back(vertices[i]);
		}

		vector <int> contour;

		//cele 6 fete ale paralelipipedului

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

	Paralellipiped(Point3D* startPoint,float L, float h, float l,Color _color,bool _fill){

		this->startPoint = startPoint;
		this->L = L;
		this->l = l;
		this->h = h;

		computeCenter();

		/*
		vertices.push_back(new Point3D(0, 0, 0));
		vertices.push_back(new Point3D(L, 0, 0));
		vertices.push_back(new Point3D(L, 0, l));
		vertices.push_back(new Point3D(0, 0, l));
		//varfurile de sus
		vertices.push_back(new Point3D(0, h, 0));
		vertices.push_back(new Point3D(L, h, 0));
		vertices.push_back(new Point3D(L, h, l));
		vertices.push_back(new Point3D(0, h, l));

		*/


		float x = startPoint->x;
		float y = startPoint->y;
		float z = startPoint->z;

		vertices.push_back(new Point3D(x, y, z));
		vertices.push_back(new Point3D(x+L, y, z));
		vertices.push_back(new Point3D(x+L, y, z+l));
		vertices.push_back(new Point3D(x, y, z+l));
		//varfurile de sus
		vertices.push_back(new Point3D(x, y+h, z));
		vertices.push_back(new Point3D(x+L, y+h, z));
		vertices.push_back(new Point3D(x+L, y+h, z+l));
		vertices.push_back(new Point3D(x, y+h, z+l));

		for (int i = 0; i < vertices.size(); i++){
			transf_vertices.push_back(vertices[i]);
		}

		vector <int> contour;

		//cele 6 fete ale paralelipipedului

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


		color.r = _color.r;
		color.g = _color.g;
		color.b = _color.b;

		fill = _fill;
	}

	void computeCenter(){
		center = new Point3D();

		center->x = startPoint->x + L / 2;
		center->y = startPoint->y + h / 2;
		center->z = startPoint->z + l / 2;
	}

	~Paralellipiped(){

	}

};