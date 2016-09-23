#pragma once
//#include <vector>
//#include "Framework/Point2D.h"
#include "Framework/Object3D.h"

class Pyramid :public Object3D{

public:


	int type;	//1 inseamna ca e piramida cu baza triunghi
				//2 inseamna ca epiramida cu baza patrat
				//3 trunchi piramida

	float l;	//latura baza
	float h;	//inaltime piramida
	float circumRadius;	//raza cercului circumscris bazei

	Point3D* startPoint;
	Point3D* center;
	/*
	Pyramid(Point3D* startPoint,int type,float l,float h){

		this->l = l;
		this->h = h;

		//this->startPoint = startPoint;

		this->type = type;
		vector <int> contour;

		switch (type){


			case 1:{
					   fill = false;
					   color.r = color.b = color.r = 0;

			};
			break;
			
			case 2:{


					   vertices.push_back(new Point3D(0, 0, 0));
					   vertices.push_back(new Point3D(l, 0, 0));
					   vertices.push_back(new Point3D(l, l, 0));
					   vertices.push_back(new Point3D(0, l, 0));
					   vertices.push_back(new Point3D(l / 2, l / 2, h));

					   contour.clear();
					   contour.push_back(0);
					   contour.push_back(1);
					   contour.push_back(2);
					   contour.push_back(3);
					   faces.push_back(new Face(contour));

					   contour.clear();
					   contour.push_back(0);
					   contour.push_back(1);
					   contour.push_back(4);
					   faces.push_back(new Face(contour));

					   contour.clear();
					   contour.push_back(1);
					   contour.push_back(2);
					   contour.push_back(4);
					   faces.push_back(new Face(contour));

					   contour.clear();
					   contour.push_back(2);
					   contour.push_back(3);
					   contour.push_back(4);
					   faces.push_back(new Face(contour));

					   contour.clear();
					   contour.push_back(0);
					   contour.push_back(3);
					   contour.push_back(4);
					   faces.push_back(new Face(contour));


					   fill = false;
					   color.r = color.b = color.r = 0;

			};
			break;

		}

	}
	*/

	Pyramid(Point3D* startPoin,int type,float l,float h,Color _color, bool _fill){

		vector <int> contour;
		this->type = type;

		this->l = l;
		this->h = h;
		this->circumRadius = l / sqrt(3);


		switch (type){


		case 1:{

				  // vertices.push_back(new Point3D(0, 0, 0));
				  // vertices.push_back(new Point3D(l, 0, 0));
				  // vertices.push_back(new Point3D(l, l, 0));
				  //vertices.push_back(new Point3D(l / 2, l / 2, h));

				   fill = _fill;

				   color.r = _color.r;
				   color.g = _color.g;
				   color.b = _color.b;


		};
			break;

		case 2:{

				   Point3D* startPoint = new Point3D(300,0,-200);

				   vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z));
				   vertices.push_back(new Point3D(l+startPoint->x, startPoint->y, startPoint->z));
				   vertices.push_back(new Point3D(startPoint->x+l, startPoint->y, startPoint->z-l));
				   vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z-l));
				   vertices.push_back(new Point3D(startPoint->x+l/2, startPoint->y+h, startPoint->z-l/2));

				   contour.clear();
				   contour.push_back(0);
				   contour.push_back(1);
				   contour.push_back(2);
				   contour.push_back(3);
				   faces.push_back(new Face(contour));

				   contour.clear();
				   contour.push_back(0);
				   contour.push_back(1);
				   contour.push_back(4);
				   faces.push_back(new Face(contour));

				   contour.clear();
				   contour.push_back(1);
				   contour.push_back(2);
				   contour.push_back(4);
				   faces.push_back(new Face(contour));

				   contour.clear();
				   contour.push_back(2);
				   contour.push_back(3);
				   contour.push_back(4);
				   faces.push_back(new Face(contour));

				   contour.clear();
				   contour.push_back(0);
				   contour.push_back(3);
				   contour.push_back(4);
				   faces.push_back(new Face(contour));

				   fill = _fill;

				   color.r = _color.r;
				   color.g = _color.g;
				   color.b = _color.b;

		};
			break;

		}



	}

	//pentru corpurile asupra carora voi imprima o miscare circulara 
	//trebuie sa calculez raza cercului circumscris bazei acestora
	
	float baseArea(){

	}

	~Pyramid(){

	}


};