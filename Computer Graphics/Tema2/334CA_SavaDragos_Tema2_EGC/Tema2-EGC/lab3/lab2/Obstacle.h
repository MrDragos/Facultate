//Sava
//Dragos
//334CA


#pragma once
#include <iostream>
#include <vector>
#include "Framework/Object3D.h"
#include <cmath>
#include "Sphere.h"
#include "Cube.h"
#include "Paralellipiped.h"
#include "Pyramid.h"
#include "Framework\DrawingWindow.h"
#include "Framework\Transform3D.h"

using namespace std;

class Obstacle{

public:

	int behaviour;	//1 pentru static
					//2 pentru dinamic

	bool remove = false;	//cand este true stergem obiectul din contextul vizual
	//bool isOut;

	int type;	//tipul de obstacol

	float tx;
	float ty;
	float circumRadius;	//raza cercului circumscris razei
	float tz;
	bool alreadyColided;	//obiectul a fost implicat intr-un accident
	bool outRunned;	//obiectul fie ca e obstacol sau alt vehicul a fost depasit de masina noastra

	float x;
	float y;
	float z;

	float size;
	int score;



	float speed;	//viteza cu care obiectele vor veni spre masina
	Point3D* center;

	vector <Object3D*> components;
	//Point3D* center;
	vector <Point3D*> collisionAreaPoints;	//contine punctele aflate pe muchiile bazei masinii
	

	Obstacle(int type,float x,float y,float z){

		tx = 0;
		ty = 0;
		tz = 0;

		outRunned = false;
		alreadyColided = false;

		this->x = x;
		this->y = y;
		this->z = z;

		this->type = type;

		switch (type){

			//Cub
			case 1:{

			   this->score = 500;

				size = 5;

				Object3D* cub = new Cube(size, new Point3D(x, y, z), Color(0.47, 0.41, 0.91), true);
				components.push_back(cub);

				this->circumRadius = size*sqrt(2);

				this->center = new Point3D();
				this->center->x = x  + size / 2;
				this->center->y = 0;
				this->center->z = z - size / 2;


			};
				break;

			//Parallelipiped dreptunghic
			case 2:{

					   this->score = 500;


					   float L = 4;
					   float l = 4;
					   float h = 10;

				 Object3D* paral = new Paralellipiped(new Point3D(x, y, z), L, h, l, Color(0, 0.4, 0), false);
				 components.push_back(paral);

				 this->center = new Point3D();
				 this->center->x = x + L / 2;
				 this->center->y = 0;
				 this->center->z = z - l / 2;

				 this->circumRadius = (L + l) / 2;

			};
				break;

			case 3:{
					   this->score = 100;

					   vector <Point3D*> vertices;
					   vector <Face*> faces;
					   vector <int> contour;


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

					   //Point3D *center = new Point3D(600, 100, -200);

					   Point3D* center = new Point3D(x,y,z);

					   float radius = n / 2;

					   

					   for (phi = 0; phi < 2 * PI; phi += PI / d) {
						   // AICI INCEPE
						   contour.clear();
						   for (theta = 0; theta <= PI; theta += PI / d) {
							   Point3D *p = new Point3D();
							   p->x = center->x + radius * cos(phi) * sin(theta);
							   p->y = center->y + radius * sin(phi) * sin(theta);
							   p->z = center->z + radius * cos(theta);

							   vertices.push_back(p);
							   //transf_vertices.push_back(p);
							   contour.push_back(i);
							   i++;
						   }
						   faces.push_back(new Face(contour));
						   // AICI SE TERMINA
					   }


					   Object3D* sfera = new Object3D(vertices,faces);
					   components.push_back(sfera);

					   this->center = new Point3D();
					   this->center->x = x;
					   this->center->y = 0;
					   this->center->z = z;

					   this->circumRadius = radius;


			};
				break;

			case 4:{


					   this->score = 100;

					   vector <Point3D*> vertices;
					   vector <Face*> faces;
					   vector <int> contour;

					   float l = 80;
					   float h = 200;

					   this->center = new Point3D();
					   this->center->x = x + l/2;
					   this->center->y = 0;
					   this->center->z = z-l/2;


					   Point3D* startPoint = new Point3D(x, y, z);

					   vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z));
					   vertices.push_back(new Point3D(l + startPoint->x, startPoint->y, startPoint->z));
					   vertices.push_back(new Point3D(startPoint->x + l, startPoint->y, startPoint->z - l));
					   vertices.push_back(new Point3D(startPoint->x, startPoint->y, startPoint->z - l));
					   vertices.push_back(new Point3D(startPoint->x + l / 2, startPoint->y + h, startPoint->z - l / 2));

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
					   contour.push_back(3);
					   contour.push_back(0);
					   contour.push_back(4);
					   faces.push_back(new Face(contour));

					   Object3D* piramida = new Object3D(vertices, faces, Color(0, 0, 1), false);
					   //addObject3D_to_Visual2D(piramida, v2d1); */
					   components.push_back(piramida);
				
					   this->circumRadius = l;


			};
				break;

			default:
				break;

		}

	}



	void generateCollisionAreaPoints(){

	}

	void addObstacle_to_Visual2D(Visual2D *v){
		for (int i = 0; i < components.size(); i++){
			DrawingWindow::addObject3D_to_Visual2D(components[i], v);
		}
	}

	void removeObstacle_from_Visual2D(Visual2D *v){
		for (int i = 0; i < components.size(); i++){
			DrawingWindow::removeObject3D_from_Visual2D(components[i], v);
		}
	}

	void applyTransform(){
		for (int i = 0; i < components.size(); i++){
			Transform3D::applyTransform(components[i]);
		}
	}


	~Obstacle(){}



};