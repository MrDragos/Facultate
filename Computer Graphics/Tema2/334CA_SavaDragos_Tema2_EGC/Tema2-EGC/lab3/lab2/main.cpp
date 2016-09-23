//Sava
//Dragos
//334CA

#include "Framework/DrawingWindow.h"
#include "Framework/Visual2D.h"
#include "Framework/Transform2D.h"
#include "Framework/Transform3D.h"
#include "Framework/Line2D.h"
#include "Framework/Rectangle2D.h"
#include "Framework/Circle2D.h"
#include "Framework/Polygon2D.h"
#include "Framework/Object3D.h"
#include "Cube.h"
#include "Obstacle.h"
#include "Paralellipiped.h"
#include "Pyramid.h"
#include "Rectangle3D.h"
#include "TrapezoidPrism.h"
#include "Car.h"
#include "Line3D.h"
#include <iostream>
#include <string>
#include <cmath>
//#include <vector>
#include <cstdlib>
#include <ctime>
#include <cstdio>
#include <sstream>
#include <windows.h>


#define PI 3.14159265358979323846
//#define PI 3.14159
#define inf 1000000

using namespace std;

Visual2D *v2d1,*v2d2;
Object3D *cube11,*cube21,*cube31;
Object3D *pyramid;
Object3D *rect3D,*rect3D1,*rect3D2,*rect3D3;
Object3D *sphere;
Object3D *bandLine1, *bandLine2, *bandLine3, *bandLine4;
Rectangle2D *rect;
Line2D *banda;
Cube *cub,*cub1;
Object3D *sfera;
Object3D *circle3D;
Object3D *testParal;
Object3D *testrect;
Object3D *testPrisma;
Object3D *paral;
Object3D* sf;
Obstacle* obstacle;


vector <Line3D*> fragmentsLines1;
vector <Line3D*> fragmentsLines2;
vector <Line3D*> fragmentsLines3;
vector <Line3D*> fragmentsLines4;
vector <Obstacle*> obstacles;

float carSpeed = 15;
float vehicleSpeed = 0.002;

float MaxSpeed = 100;
float MinSpeed = 0.1;

int numObjects = 50;	//numar maxim de obiecte ce vor fi create cu generateObstacles

Car* car;

ostringstream convert;

//Variabile pentru scor
Text *score_text;
string score_string;
int score = 0;

//Variabile pentru distanta
Text *distance_text;
string distance_string;
float road = 0;	//distanta parcursa

Line3D* tempLine;

//Variabile pentru timp
Text *time_text;
string time_string;
int seconds = 0;
int minutes = 0;

//Variabile pentru vieti

Text *lifes_text;
string lifes_string;
float lifes_number = 10;	//distanta parcursa


float myWidth;
float myHeight;

Object3D *grass;
Object3D* testcar;

//float n=1;

//clock_t start, time;
time_t start_time;
time_t end_time;
int gameTime;


time_t t1;
time_t t2;
int interval;


Line3D* testLine,*testLine1,*testLine2,*testLine3;

//calculeaza distanta dintre 2 puncte in spatiu 3D
float distance3D(Point3D *a,Point3D *b){
	return sqrt(pow(a->x-b->x,2)+pow(a->y-b->y,2)+pow(a->z-b->z,2));
}

void verifyCollision(){

}

Text *game_text;
string game_over;


void gameOver(){
	game_over.append("GAME OVER");
	game_text = new Text(game_over, Point2D(500, 100), Color(0.5, 0.5, 0), BITMAP_TIMES_ROMAN_24);
	DrawingWindow::addText_to_Visual2D(game_text, v2d2);
	game_over.clear();
}

void updateLifes(){

	DrawingWindow::removeText_from_Visual2D(lifes_text, v2d2);

	lifes_string.append("Lifes: ");


	lifes_number = car->lifes_number;
	
	if (lifes_number < 0)
		lifes_number = 0;


	convert << lifes_number;
	lifes_string.append(convert.str());
	convert.str("");
	convert.clear();

	lifes_text = new Text(lifes_string, Point2D(100, 100), Color(1, 0, 0), BITMAP_TIMES_ROMAN_24);
	DrawingWindow::addText_to_Visual2D(lifes_text, v2d2);
	lifes_string.clear();
}

void updateTime(){

	DrawingWindow::removeText_from_Visual2D(time_text, v2d2);

	time_string.append("Time ");

	time(&end_time);

	gameTime = difftime(end_time,start_time);
	


	
	minutes = gameTime / 60;

	convert << minutes;
	time_string.append(convert.str());
	convert.str("");
	convert.clear();

	time_string.append(" : ");
	seconds = gameTime % 60;


	convert << seconds;
	time_string.append(convert.str());
	convert.str("");
	convert.clear();

	time_text = new Text(time_string, Point2D(300, 100), Color(1, 0, 0), BITMAP_TIMES_ROMAN_24);
	DrawingWindow::addText_to_Visual2D(time_text, v2d2);
	time_string.clear();
}

void updateDistance(){

	DrawingWindow::removeText_from_Visual2D(distance_text, v2d2);
	distance_string.append("Distance:   ");
	road += carSpeed/20;
	convert << (int)(road/25);

	if ((road / 25) >= 1000){
		gameOver();
	}
	else{
		distance_string.append(convert.str());
		distance_string.append("  /1000 Km");
		convert.str("");
		convert.clear();
		distance_text = new Text(distance_string, Point2D(500, 100), Color(1, 0, 0), BITMAP_TIMES_ROMAN_24);
		DrawingWindow::addText_to_Visual2D(distance_text, v2d2);
		distance_string.clear();
	}


}

void updateScore(){

	DrawingWindow::removeText_from_Visual2D(score_text, v2d2);
	score_string.append("Score : ");

	score = car->score;

	convert << score;
	score_string.append(convert.str());
	convert.str("");
	convert.clear();
	score_text = new Text(score_string, Point2D(900, 100), Color(1, 0, 0), BITMAP_TIMES_ROMAN_24);
	DrawingWindow::addText_to_Visual2D(score_text, v2d2);
	score_string.clear();
}

vector <Point3D*> vertices;
vector <Face*> faces;
vector <int> contour;
vector <Point3D*> transf_vertices;

void drawRoad(){
	
	vertices.clear();
	contour.clear();
	faces.clear();

	int z = -10000;

	vertices.clear();
	faces.clear();

	vertices.push_back(new Point3D(0, 0, z));
	vertices.push_back(new Point3D(myWidth, 0, z));
	vertices.push_back(new Point3D(myWidth, 0, 0));
	vertices.push_back(new Point3D(0, 0, 0));

	contour.push_back(0);
	contour.push_back(1);
	contour.push_back(2);
	contour.push_back(3);
	faces.push_back(new Face(contour));

	rect3D = new Object3D(vertices, faces, Color(0.5, 0.5, 0.5), true);
	DrawingWindow::addObject3D_to_Visual2D(rect3D, v2d1);
}



float newz = -10000;


//functia nu genereaza numai obstacole
//obiectele Obstacle de tipul 1 si 2 sunt vehicule
//iar cele de tipul 3 si 4 sunt obstacole propriu zice
void generateObstacles(){

	//vector <int> xCoordinates;
	
	int xCoordinates[11] = {0,100,200,300,400,500,600,700,800,900,1000};
	//Obstacle* temp_obstacle;
	int type = rand() % 4+1;	//tipul de obstacol
	float x = xCoordinates[rand() % 11];		//pozitia de unde pleaca
	
	//int type = 3;
	//float x = 50;
	Obstacle* temp_obstacle;
	float y = 0;
	float z = -10000  + 100*(rand()%10);

	if (type == 1 || type == 2){
		z = -900;
		x = x / 10;
	}
		

	//temp_obstacle = new Obstacle(1,50,0,-950);
	temp_obstacle = new Obstacle(type,x,y,z);
	obstacles.push_back(temp_obstacle);
	temp_obstacle->addObstacle_to_Visual2D(v2d1);
	//newz += 500;
	//int localTime;




}

void drawGrass(){
	vertices.clear();
	contour.clear();
	faces.clear();

	int z = -10000;



	vertices.push_back(new Point3D(-myWidth * 4, 0, z));
	vertices.push_back(new Point3D(myWidth * 5, 0, z));
	vertices.push_back(new Point3D(myWidth * 5, 0, 0));
	vertices.push_back(new Point3D(-myWidth * 4, 0, 0));

	contour.push_back(0);
	contour.push_back(1);
	contour.push_back(2);
	contour.push_back(3);
	faces.push_back(new Face(contour));

	grass = new Object3D(vertices, faces, Color(0.2, 1, 0.3), true);
	DrawingWindow::addObject3D_to_Visual2D(grass, v2d1);

}




//functia care permite adaugarea de obiecte
void DrawingWindow::init()
{
	
	time(&start_time);



	time(&end_time);

	gameTime = difftime(end_time,start_time);
	cout << gameTime << endl;

	
	v2d1 = new Visual2D(0, 0, DrawingWindow::width, DrawingWindow::height, 0, DrawingWindow::height / 6, DrawingWindow::width, DrawingWindow::height);
	//v2d1->tipTran(true);
	addVisual2D(v2d1);
	//v2d1->tipTran(true);

	v2d2 = new Visual2D(0, 0, DrawingWindow::width, DrawingWindow::height, 0, 0, DrawingWindow::width, DrawingWindow::height / 6);
	//v2d2->tipTran(true);
	addVisual2D(v2d2);


	glClearColor(0.529f, 0.807f, 0.921f, 1.0f);

	myWidth = DrawingWindow::width;
	myHeight = DrawingWindow::height;
	

	//CONSTRUIESC MASINA

	car = new Car();
	car->addCar_to_Visual2D(v2d1);




	for (int i = 0; i < numObjects; i++){
		generateObstacles();
	}
	

	
	testLine = new Line3D(new Point3D(0, 0, 0), new Point3D(0, 0, -1000), 5, Color(1, 0, 0));
	addObject3D_to_Visual2D(testLine, v2d1);



	testLine1 = new Line3D(new Point3D(myWidth/3, 0, 0), new Point3D(myWidth/3, 0, -1000), 5, Color(1, 0, 0));
	addObject3D_to_Visual2D(testLine1, v2d1);

	testLine2 = new Line3D(new Point3D(myWidth*2 / 3, 0, 0), new Point3D(myWidth*2 / 3, 0, -1000), 5, Color(1, 0, 0));
	addObject3D_to_Visual2D(testLine2, v2d1);

	testLine3 = new Line3D(new Point3D(myWidth, 0, 0), new Point3D(myWidth, 0, -1000), 5, Color(1, 0, 0));
	addObject3D_to_Visual2D(testLine3, v2d1);

	drawRoad();

	drawGrass();

	Line2D *line = new Line2D(Point2D(0,0.01),Point2D(DrawingWindow::width,0.01),Color(1,0,0));
	addObject2D_to_Visual2D(line, v2d2);

	time(&t1);

}

float tz = 0;
float ty = 0;
float tx = 0;

float u = 0;

int ciclu = 10;
int nr_cicli = 0;


int nsize = 1;
vector <int> indexi;
int k;

//functia care permite animatia
void DrawingWindow::onIdle()
{
	updateDistance();
	updateTime();
	updateScore();
	updateLifes();

	Transform3D::loadIdentityProjectionMatrix();
	Transform3D::perspectiveProjectionMatrix(myWidth / 2, myHeight/2, 900);
	Transform3D::loadIdentityModelMatrix();
	car->applyTransform();

	//PERSPECTIVA SOSEA
	Transform3D::loadIdentityProjectionMatrix();
	Transform3D::perspectiveProjectionMatrix(myWidth/2, myHeight*1.1, 1000);
	Transform3D::loadIdentityModelMatrix();
	Transform3D::applyTransform(rect3D);
	Transform3D::applyTransform(testLine);


	Transform3D::loadIdentityProjectionMatrix();
	Transform3D::perspectiveProjectionMatrix(myWidth/2, myHeight*150, 1000);
	Transform3D::loadIdentityModelMatrix();
	Transform3D::applyTransform(grass);

	Transform3D::loadIdentityProjectionMatrix();
	Transform3D::perspectiveProjectionMatrix(myWidth /6, myHeight*1.1, 1000);
	Transform3D::loadIdentityModelMatrix();
	Transform3D::applyTransform(testLine1);


	Transform3D::loadIdentityProjectionMatrix();
	Transform3D::perspectiveProjectionMatrix(-myWidth/6, myHeight*1.1, 1000);
	Transform3D::loadIdentityModelMatrix();
	Transform3D::applyTransform(testLine2);

	
	Transform3D::loadIdentityProjectionMatrix();
	Transform3D::perspectiveProjectionMatrix(-myWidth/2, myHeight*1.1, 1000);
	Transform3D::loadIdentityModelMatrix();
	Transform3D::applyTransform(testLine3);

	int tempid;
	
		time(&t2);
		interval = difftime(t2,t1);
		

		if (interval == 7){
			//cout << interval << endl;
			nr_cicli++;
			//cout << nr_cicli << endl;
			nsize++;
			
			time(&t1);

		}

		//int a =0;
	for (int i = 0; i < nsize; i++){


//setez viteza pentru obstacole
//si celelalte vehicule
		if (obstacles[i]->type > 2)
			obstacles[i]->tz += carSpeed;
		else
			obstacles[i]->tz += vehicleSpeed;

		//daca obiectul a iesit din campul meu vizual , il sterg
		if (obstacles[i]->z + obstacles[i]->tz >= 0){
			
			
			if (indexi.empty())
				indexi.push_back(i);

			k = 0;

			for (int j = 0; j < indexi.size(); j++)
				if (i == indexi[j]){
					k++;
					break;
				}

				if (k == 0){
					indexi.push_back(i);
					obstacles[i]->removeObstacle_from_Visual2D(v2d1);
				}

		}

		car->collisionDetection(obstacles[i]);
		car->outrunn(obstacles[i]);

		Transform3D::loadIdentityProjectionMatrix();
		Transform3D::perspectiveProjectionMatrix(myWidth / 2, myHeight*1.1, 1000);
		Transform3D::loadIdentityModelMatrix();
		Transform3D::translateMatrix(0, 0, obstacles[i]->tz);
		obstacles[i]->applyTransform();



	}


}

void isOut(){

}

//functia care se apeleaza la redimensionarea ferestrei
void DrawingWindow::onReshape(int width,int height)
{
	
	//v2d1->poarta(0,0,width,height); 
	v2d2->poarta(0, 0, width, height / 6);
	v2d1->poarta(0, height / 6, width, height);
	

}

//functia care defineste ce se intampla cand se apasa pe tastatura
void DrawingWindow::onKey(unsigned char key)
{
	switch(key)
	{


		
		case KEY_UP : {

						  if (car->tz > 0)
							  car->tz = 0;

						  if (carSpeed < MaxSpeed)
							  carSpeed += 0.25;

						  car->tz -= 0.1;


						  Transform3D::loadIdentityProjectionMatrix();
						  Transform3D::perspectiveProjectionMatrix(myWidth / 2, myHeight, 300);
						  Transform3D::loadIdentityModelMatrix();
						  Transform3D::translateMatrix(0, 0, car->tz);
						  car->applyTransform();
		};
			break;

		case KEY_DOWN : {


							if (car->tz < 0)
								car->tz = 0;

							car->tz += 0.1;

							if (carSpeed > MinSpeed)
								carSpeed -= 0.25;


							Transform3D::loadIdentityProjectionMatrix();
							Transform3D::perspectiveProjectionMatrix(myWidth / 2, myHeight, 900);
							Transform3D::loadIdentityModelMatrix();
							Transform3D::translateMatrix(0, 0, car->tz);
							car->applyTransform();


		};
			break;
			
		case KEY_RIGHT : {

							 if (car->tx < 0)
								 car->tx = 0;

			car->tx += 1;
			
			Transform3D::loadIdentityProjectionMatrix();
			Transform3D::perspectiveProjectionMatrix(myWidth / 2, myHeight/2, 900);
			Transform3D::loadIdentityModelMatrix();
			Transform3D::translateMatrix(car->tx,0,0);
			car->applyTransform();



		};
			break;

		case KEY_LEFT : {

							if (car->tx > 0)
								car->tx = 0;

				car->tx -= 1;

				Transform3D::loadIdentityProjectionMatrix();
				Transform3D::perspectiveProjectionMatrix(myWidth / 2, myHeight/2, 900);
				Transform3D::loadIdentityModelMatrix();
				Transform3D::translateMatrix(car->tx, 0, 0);
				car->applyTransform();

	

		};
			break;

		//maresc viteza masinii
		case 'a': {
					  if (carSpeed < MaxSpeed)
						carSpeed += 0.25;
		};
			break;


			//reduc viteza masinii
		case 'r': {
					  if (carSpeed > MinSpeed)
						carSpeed -= 0.25;
		};
			break;

		case 27 : exit(0);
	}
}

//functia care defineste ce se intampla cand se da click pe mouse
void DrawingWindow::onMouse(int button,int state,int x, int y)
{
	
}


int main(int argc, char** argv)
{
	//creare fereastra
	//DrawingWindow dw(argc, argv, 600, 600, 200, 50, "Laborator EGC");
	DrawingWindow dw(argc, argv, 1280, 640, 0, 0, "Tema2 EGC");
	//se apeleaza functia init() - in care s-au adaugat obiecte
	dw.init();
	//se intra in bucla principala de desenare - care face posibila desenarea, animatia si procesarea evenimentelor
	
	dw.run();
	//cout << "BUUUUUU" << endl;
	return 0;

}