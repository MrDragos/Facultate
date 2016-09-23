//Sava
//Dragos
//334CA


#pragma once
#include "Framework/Object2D.h"
#include "Framework/Polygon2D.h"
#include "Framework/Circle2D.h"
#include "Framework/Point2D.h"
#include "Framework/Transform2D.h"
#include "Framework/Rectangle2D.h"
//#include "Framework/DrawingWindow.h"
#include "Framework/Visual2D.h"
#include <Vector>
#include <iostream>
#include <ctime>
#include <cstdlib>


using namespace std;

class Enemy{

public:

	Visual2D* v2d;
	vector<Object2D*> components;
	int type;	//indica tipul de inamic 1 , 2 sau 3

	int radius;//raza cercului care este descris de obiect in timpul miscarii circulare

	bool letItLive = true;

	int r = 8;//pt enemy1

	//pt enemy 2
	int w = 40;
	int h = 40;

	int score;
	int cadru;//cadrul in care apare initial inamicul pe ecran 

	int collisionID = 0;


	Point2D *center;
	Circle2D *circle3;
	Polygon2D *triangle1, *triangle2, *triangle3, *triangle4;
	Rectangle2D *rect1;

	int aux;
	int auy;

	float tx = 0;
	float ty = 0;

	float angle = 0;
	float rotate_speed;
	float translate_speed;

	bool outOfBounds = false;
	bool alreadyColided = false;

	//pt enemy 3
	int w2 = 10;
	int h2 = 10;


	int upperLimit;
	int  lowerLimit;
	int leftLimit;
	int rightLimit;

	void setBounds(int upperLimit, int lowerLimit, int leftLimit, int rightLimit){

		this->lowerLimit = lowerLimit;
		this->upperLimit = upperLimit;
		this->leftLimit = leftLimit;
		this->rightLimit = rightLimit;

	}

	Enemy(int type,Visual2D* v2d,Point2D* center ){
		
		//this->type = (rand()%100)%3+1;
		this->v2d = v2d;
		this->type = type;

		this->center = center;



		switch (type)
		{
			case 1:
			{

					  rotate_speed = 2;
					  translate_speed = 0.5;

					  score = 25;

					 radius = r+r*2*sqrt(2);
					 //center = new Point2D(100, 100);
					 
					 //pentru testarea razei
					 //Circle2D *test = new Circle2D(Point2D(center->x, center->y), radius, Color(1, 0.5, 0), false);
					 //components.push_back(test);

					  circle3 = new Circle2D(Point2D(center->x, center->y), r, Color(1, 0, 0), false);
					  components.push_back(circle3);
					 // addObject2D(circle3);

					  triangle2 = new Polygon2D(Color(0, 1, 0), true);
					  triangle2->addPoint(Point2D(center->x + r, center->y));
					  triangle2->addPoint(Point2D(center->x + 3 * r, center->y));
					  triangle2->addPoint(Point2D(center->x + 3 * r, center->y - 2 * r));
					  components.push_back(triangle2);
					  //addObject2D(triangle2);

					  triangle1 = new Polygon2D(Color(0, 1, 0), true);
					  triangle1->addPoint(Point2D(center->x, center->y + r));
					  triangle1->addPoint(Point2D(center->x, center->y + 3 * r));
					  triangle1->addPoint(Point2D(center->x + 2 * r, center->y + r * 3));
					  components.push_back(triangle1);
					  //addObject2D(triangle1);


					  triangle3 = new Polygon2D(Color(0, 1, 0), true);
					  triangle3->addPoint(Point2D(center->x - r, center->y));
					  triangle3->addPoint(Point2D(center->x - 3 * r, center->y));
					  triangle3->addPoint(Point2D(center->x - 3 * r, center->y + 2 * r));
					  components.push_back(triangle3);
					  //addObject2D(triangle3);


					  triangle4 = new Polygon2D(Color(0, 1, 0), true);
					  triangle4->addPoint(Point2D(center->x, center->y - r));
					  triangle4->addPoint(Point2D(center->x, center->y - 3 * r));
					  triangle4->addPoint(Point2D(center->x - 2 * r, center->y - 3 * r));
					  components.push_back(triangle4);
					  //addObject2D(triangle4);



			}
				break;
			case 2:
			{

					  rotate_speed = 1;
					  translate_speed = 0.2;

					  score = 100;

					  //center = new Point2D(400, 400);//centrul corpului
					  radius = w*sqrt(2)/2;
					  //pentru test
					  //Circle2D *test = new Circle2D(Point2D(center->x, center->y), radius, Color(1, 0.5, 0), false);
					  //components.push_back(test);

					  rect1 = new Rectangle2D(Point2D(center->x-w/2, center->y-h/2), w, h, Color(1, 0, 0), false);
					  components.push_back(rect1);
					  //addObject2D(rect1);

					  rect1 = new Rectangle2D(Point2D(center->x - w / 4, center->y - h / 4), w / 2, h / 2, Color(0, 1, 0), false);
					  components.push_back(rect1);
					  //addObject2D(rect1);

					  rect1 = new Rectangle2D(Point2D(center->x - w / 8, center->y - h / 8), w / 4, h / 4, Color(0, 0, 1), true);
					  components.push_back(rect1);
					  //addObject2D(rect1);


				  
			};
				break;

			case 3:
			{
					  rotate_speed = 2.5;
					  translate_speed = 0.2;

					  score = 50;


					  radius = w2*(1+sqrt(2)/2);
					  //pentru test
					  //Circle2D *test = new Circle2D(Point2D(center->x, center->y), radius, Color(1, 0.5, 0), false);
					  //components.push_back(test);

					  Point2D *temp = new Point2D(center->x, center->y);

					  center = new Point2D(temp->x-w2/2,temp->y-h2/2);

					  rect1 = new Rectangle2D(Point2D(center->x, center->y), w2, h2, Color(0, 0, 1), true);
					  components.push_back(rect1);
					  //addObject2D(rect1);

					  triangle1 = new Polygon2D(Color(0, 1, 0), true);
					  triangle1->addPoint(Point2D(center->x, center->y));
					  triangle1->addPoint(Point2D(center->x + w2, center->y));
					  triangle1->addPoint(Point2D(center->x + w2 / 2, center->y - h2));
					  components.push_back(triangle1);
					  //addObject2D(triangle1);

					  triangle2 = new Polygon2D(Color(0, 1, 0), true);
					  triangle2->addPoint(Point2D(center->x, center->y));
					  triangle2->addPoint(Point2D(center->x, center->y + h2));
					  triangle2->addPoint(Point2D(center->x - w2, center->y + h2 / 2));
					  components.push_back(triangle2);
					  //addObject2D(triangle2);


					  triangle3 = new Polygon2D(Color(0, 1, 0), true);
					  triangle3->addPoint(Point2D(center->x, center->y + h2));
					  triangle3->addPoint(Point2D(center->x + w2, center->y + h2));
					  triangle3->addPoint(Point2D(center->x + w2 / 2, center->y + 2 * h2));
					  components.push_back(triangle3);
					  //addObject2D(triangle3);

					  triangle4 = new Polygon2D(Color(0, 1, 0), true);
					  triangle4->addPoint(Point2D(center->x + w2, center->y + h2));
					  triangle4->addPoint(Point2D(center->x + w2, center->y));
					  triangle4->addPoint(Point2D(center->x + 2 * w2, center->y + h2 / 2));
					  components.push_back(triangle4);
					  //addObject2D(triangle4);
					  center = new Point2D(temp->x, temp->y);

			};
				break;

			default:
				break;
			}//END switch

		}



		void applyTranform(){
			for (int i = 0; i < components.size(); i++)
				Transform2D::applyTransform(components[i]);
		}

		float distance(Point2D* a,Point2D* b){
			return sqrt( pow(a->x - b->x,2)+pow(a->y - b->y,2));
		}

		

		void weaponCollision(vector<Point2D*> weapon_points){

			//float aux = center->x + tx + translate_speed*cos(angle);
			//float auy = center->y + ty + translate_speed*sin(angle);

			float aux = center->x + tx;
			float auy = center->y + ty;

			Point2D* temp;

			temp = new Point2D(aux, auy);
			//int k = 0;

			for (int i = 0; i < weapon_points.size(); i++){

				if (distance(temp, weapon_points[i]) <= radius){
					//k++;
					letItLive = false;
					break;
				}
			}

				//cout << "Arma merge !";
			}
			
		



		void isOut(){


			aux = center->x + tx;
			auy = center->y + ty;

			if (aux - radius <= leftLimit || aux + radius >= rightLimit || auy + radius >= upperLimit || auy - radius <= lowerLimit)
				outOfBounds = true;
			else
				outOfBounds = false;
		}

		~Enemy(){

		}







};