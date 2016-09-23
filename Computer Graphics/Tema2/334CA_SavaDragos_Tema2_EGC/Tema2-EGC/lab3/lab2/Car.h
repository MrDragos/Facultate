//Sava
//Dragos
//334CA

#pragma once
#include <iostream>
#include <vector>
#include <cmath>
#include "Framework/Point3D.h"
#include "Framework/Point2D.h"
#include "Framework/Object3D.h"
#include "Cube.h"
#include "Obstacle.h"
#include "Paralellipiped.h"
#include "Pyramid.h"
#include "Framework\DrawingWindow.h"
#include "Framework\Transform3D.h"

using namespace std;

class Car{



public:

	float tx;
	float ty;
	float tz;

	int score;
	int lifes_number;

	float leftSpeed;
	float rightSpeed;
	float speed;
	float circumRadius;

	//int livesCounter;


	vector <Object3D*> components;
	Point3D* center;
	Point3D* startPoint;
	vector <Point3D*> collisionAreaPoints;	//contine punctele aflate pe muchiile bazei masinii


	Car(){

		lifes_number = 10;

		tx = 0;
		ty = 0;
		tz = 0;

		this->score = 0;

		leftSpeed = 0.1;
		rightSpeed = 0.1;

		float size = 50;


		//Object3D* cub = new Cube(size,new Point3D(600,0,-50),Color(0.17,0.41,0.91),true);
		//components.push_back(cub);

		//Trapez test

		Object3D* testPrisma = new TrapezoidPrism(new Point3D(500, 0, -100), 200, 80, 50, 100, 40, Color(0.33, 0.01, 0.1), false);
		//addObject3D_to_Visual2D(testPrisma, v2d1);
		components.push_back(testPrisma);

		this->center = new Point3D();
		this->center->x = 500 + size / 2;
		this->center->y = 0;
		this->center->z = -100 + size / 2;

		this->circumRadius = size;

		//adaug obiectele in masina
		
	}

	void addCar_to_Visual2D(Visual2D *v){
		for (int i = 0; i < components.size(); i++){
			DrawingWindow::addObject3D_to_Visual2D(components[i],v);
		}
	}

	void removeCar_from_Visual2D(Visual2D *v){
		for (int i = 0; i < components.size(); i++){
			DrawingWindow::removeObject3D_from_Visual2D(components[i],v);
		}
	}

	void applyTransform(){
		for (int i = 0; i < components.size(); i++){
			Transform3D::applyTransform(components[i]);
		}
	}

	void generateCollisionAreaPoints(){

	}



	float distance3D(Point3D* a,Point3D* b){
		return sqrt(pow(a->x - b->x, 2) + pow(a->y - b->y, 2)+pow(a->z - b->z, 2));
	}

	float distance2D(Point2D* a, Point2D* b){

		return sqrt(pow(a->x - b->x, 2) + pow(a->y - b->y, 2));
	}

	//verifica culiziunea dintre masina si obstacol
	void collisionDetection(Obstacle* obstacle){


		if (obstacle->alreadyColided == false){

			float aux = center->x + tx;
			float auz = center->z + tz;

			Point3D* temp = new Point3D(aux, 0, auz);

			aux = obstacle->center->x + obstacle->tx;
			auz = obstacle->center->z + obstacle->tz;

			if (distance(temp, new Point3D(aux, 0, auz)) <= (circumRadius + obstacle->circumRadius)){
				
				obstacle->alreadyColided = true;

				//scad numarul de vieti numai pt coliziuni cu obstacole
				if (obstacle->type > 2){
					this->lifes_number--;
					//this->score -= obstacle->score;
				}

			}
		}

	}


	void outrunn(Obstacle* obstacle){

		if (obstacle->outRunned == false){

			float aux = center->x + tx;
			float auz = center->z + tz;

			//Point3D* temp = new Point3D(aux, 0, auz);

			float aux1 = obstacle->center->x + obstacle->tx;
			float auz2 = obstacle->center->z + obstacle->tz;

			//masina a trecut de obstacol/vehicul fara sa se ciocneasca
			//if (obstacle->alreadyColided == false && temp->z < auz){
			if ( auz < auz2){
				obstacle->outRunned = true;
				this->score += obstacle->score;
			}


		}
	}

	~Car(){}

};