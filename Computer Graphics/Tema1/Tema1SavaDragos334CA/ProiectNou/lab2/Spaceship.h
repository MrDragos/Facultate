//Sava
//Dragos
//334CA


#pragma once
#include "Framework/Object2D.h"
#include "Framework/Polygon2D.h"
#include "Framework/Circle2D.h"
#include "Framework/Point2D.h"
//#include "Framework/DrawingWindow.h" 
#include "Framework/Transform2D.h"
#include <Vector>
#include "Enemy.h"
#include <Math.h>
#include <iostream>

class Spaceship{

	public:

		vector <Object2D*> components;
		vector <Point2D*> weaponPoints;	//imi vor folosi la tratarea coliziunilor pentru arma

		Point2D *center,*direction,*temp;
		Polygon2D *hexagram,*little,*weapon;
		Circle2D *circle1,*circle2;
		Visual2D *v2d;

		bool activated = false;
		bool outOfBounds = false;
		bool collided = false;
		bool weaponOut = false;

		int lives_counter = 10;

		const float a = 50;	//raza cercului
		const float d = 25;

		int radius = a;

		int aux;
		int auy;

		float tx = 0;
		float ty = 0;

		float angle = 0;
		float rotate_speed = 0.2;
		float translate_speed = 20;

		//coordonate centrului navei
		const float b = 300;//x
		const float c = 500;//y

		int upperLimit;
		int  lowerLimit ;
		int leftLimit;
		int rightLimit;

		void setBounds(int upperLimit,int lowerLimit,int leftLimit ,int rightLimit){

			this->lowerLimit = lowerLimit;
			this->upperLimit = upperLimit;
			this->leftLimit = leftLimit;
			this->rightLimit = rightLimit;

		}

		Spaceship(){

			

			center = new Point2D(b,c);
			//components.push_back(center);

			//construiesc hexagrama
			hexagram = new Polygon2D(Color(0, 1, 0), false);
			hexagram->addPoint(Point2D(b, c + a / sqrt(3)));
			hexagram->addPoint(Point2D(b - a / 6, c + a / (2 * sqrt(3))));
			hexagram->addPoint(Point2D(b - a / 2, c + a / (2 * sqrt(3))));
			hexagram->addPoint(Point2D(b - a / 3, c + 0));
			hexagram->addPoint(Point2D(b - a / 2, c + -a / (2 * sqrt(3))));
			hexagram->addPoint(Point2D(b - a / 6, c - a / (2 * sqrt(3))));
			hexagram->addPoint(Point2D(b, c - a / sqrt(3)));
			hexagram->addPoint(Point2D(b + a / 6, c - a / (2 * sqrt(3))));
			hexagram->addPoint(Point2D(b + a / 2, c - a / (2 * sqrt(3))));
			hexagram->addPoint(Point2D(b + a / 3, c + 0));
			hexagram->addPoint(Point2D(b + a / 2, c + a / (2 * sqrt(3))));
			hexagram->addPoint(Point2D(b + a / 6, c + a / (2 * sqrt(3))));

			components.push_back(hexagram);

			circle1 = new Circle2D(Point2D(b, c), a / 5, Color(0, 0, 1), true);
			components.push_back(circle1);

			circle2 = new Circle2D(Point2D(b, c), a, Color(1, 0, 0), false);
			components.push_back(circle2);

			little = new Polygon2D(Color(0, 1, 0), true);
			little->addPoint(Point2D(b + 4 * a / 5, c));
			little->addPoint(Point2D(b + 3 * a / 5, c + a / 5));
			little->addPoint(Point2D(b + 3 * a / 5, c - a / 5));
			components.push_back(little);
				
			


			weapon = new Polygon2D(Color(0, 0, 1), true);
			weapon->addPoint(Point2D(b +3*a, c));
			weapon->addPoint(Point2D(b + a/2+3 * a / 5, c + a / 3));
			weapon->addPoint(Point2D(b + a/2+3 * a / 5, c - a / 3));
			//components.push_back(weapon);
		


		}
		
		//ecuatia dreptei data de 2 puncte
		//primeste x si intoarce y

		float lineEquation(float x,Point2D* p1,Point2D* p2){
			if (p1->x != p2->x)
				//sreturn ( (x * (p1->y - p2->y) / (p1->x - p2->x)) + ((p1->x * p2->y - p2->x * p1->y) / (p1->x - p2->y)));
				return x * (p2->y - p1->y) / (p2->x - p1->x) + p1->y - p1->x*(p2->y - p1->y) / (p2->x - p1->x);
			else
				return abs(p1->y - p2->y) / 2;
		}

		//pt fiecare latura a triunghilui voi calcula ecuatia dreptei
		//apoi voi alege pe fiecare dreapta puncte aflate la distante egale
		//aceasta imi va folosi mai departe la depistarea coliziunilor

		float max(float a,float b){
			return (a > b) ? a : b;
		}

		float min(float a,float b){
			return (a < b) ? a : b;
		}

		void generateWeaponPoints(){
			//for (int i = 0; i < weapon->points.size(); i++)
				//cout <<i<< endl;

			float xa;
			float xb;
			float ya;
			float yb;

			for (int i = 0; i < weapon->points.size(); i++){


				//cout << "x= " << weapon->points[i]->x << "y= " << weapon->points[i]->y << endl;

				if (weapon->points[i]->x != weapon->points[(i + 1) % 3]->x){

					xa = min(weapon->points[i]->x, weapon->points[(i + 1) % 3]->x);
					xb = max(weapon->points[i]->x, weapon->points[(i + 1) % 3]->x);

					for (float x = xa; x <= xb; x += (xb - xa) / 50){
						weaponPoints.push_back(new Point2D(x, lineEquation(x, weapon->points[i], weapon->points[(i + 1) % 3])));
						//cout << i << "   " << x << "    " << lineEquation(x, weapon->points[i], weapon->points[(i + 1) % 3]) << endl;
					}

				}
				else{

					ya = min(weapon->points[i]->y, weapon->points[(i + 1) % 3]->y);
					yb = max(weapon->points[i]->y, weapon->points[(i + 1) % 3]->y);

					for (float y = ya; y <= yb; y += (yb - ya) / 100){
						weaponPoints.push_back(new Point2D(weapon->points[i]->x,y));
						//cout << i << "   " << y << endl;
					}

				}
			}




		}

		void applyTranform(){
			for (int i = 0; i < components.size(); i++)
				Transform2D::applyTransform(components[i]);
		}
		
		void activateWeapon(){
			if (activated == false){
				//components.push_back(weapon);
				//DrawingWindow::addObject2D_to_Visual2D(weapon, v2d);
				rotate_speed /= 1.5;
				translate_speed /= 2;
			}
				activated = true;


		}

		void deactivateWeapon(){
			//trebuie sa am grija sa nu apa mai des tasta de dezactivare ca imi 
			
			if (activated == true){
				//components.pop_back();
				//DrawingWindow::removeObject2D_from_Visual2D(weapon,v2d);
				rotate_speed *= 1.5;
				translate_speed *= 2;

			}

			activated = false;


		}

		//verifica daca obiectul a iesit din marginile terenului 
		void isOut(){
			
			//verific in avans daca viitoarea pozitie a obiectului este sau nu in afara terenului
			aux = center->x +  tx+translate_speed*cos(angle);
			auy = center->y+ty+translate_speed*sin(angle);

			//aux = center->x + tx;
			//auy = center->y + ty;

			if (aux - a <= leftLimit || aux + a >= rightLimit || auy + a >= upperLimit || auy - a <= lowerLimit)
				outOfBounds = true;
			else
				outOfBounds = false;
		}

		float distance(Point2D* a, Point2D* b){

			return sqrt(pow(a->x - b->x, 2) + pow(a->y - b->y, 2));
		}
		/*
		vector<Point2D*> tranformWeaponPoints(){

			vector<Point2D*> temp;
			
			float tempx;
			float tempy;

			for (int i = 0; i < weaponPoints.size(); i++){
				
				tempx = weaponPoints[i]->x + tx + rotate_speed*cos(angle);
				tempy = weaponPoints[i]->y + ty + rotate_speed*sin(angle);
				
				//actualizez pozitiile punctelor
				//tempx = weaponPoints[i]->x + tx;
				//stempy = weaponPoints[i]->y + ty;
				
				temp.push_back(new Point2D(tempx,tempy));
			}
			
			return temp;
		
		}
		*/
		void collisionDetection(Enemy* enemy){

			//aux = center->x + tx + translate_speed*cos(angle);
			//auy = center->y + ty + translate_speed*sin(angle);

			aux = center->x + tx;
			auy = center->y + ty;

			temp = new Point2D(aux,auy);
			
			aux = enemy->center->x + enemy->tx ;
			auy = enemy->center->y + enemy->ty ;

			if (distance(temp, new Point2D(aux,auy)) <= ( radius + enemy->radius)){
	
				//if (enemy->collisionID == 0){
					lives_counter--;
				//}
	
				//enemy->collisionID = 1;

				if (lives_counter <= 0)
					lives_counter = 0;

				enemy->alreadyColided = true;

			}

		}

		~Spaceship(){

		}



};