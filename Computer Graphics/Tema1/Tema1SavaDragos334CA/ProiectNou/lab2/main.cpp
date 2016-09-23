//Sava
//Dragos
//334CA



#include "Framework/DrawingWindow.h"
#include "Framework/Visual2D.h"
#include "Framework/Transform2D.h"
#include "Framework/Line2D.h"
#include "Framework/Rectangle2D.h"
#include "Framework/Circle2D.h"
#include "Framework/Polygon2D.h"
#include "Spaceship.h"
#include <iostream>
#include <windows.h>
#include <ctime>
#include <sstream>
#include <String>
#include "Enemy.h"



#define PI 3.14159265358979323846

using namespace std;

int totalScore = 0;


clock_t start, final;

int collisionID = 0;

//Polygon2D *hexagram;
Visual2D *v2d1,*v2d2;
Rectangle2D *rect;

float gametime = 0;
int time_counter = 0;

int upperLimit = DrawingWindow::height;
int lowerLimit = 0;
int leftLimit = 0;
int rightLimit = DrawingWindow::width;

int enemiesCounter = 0;

//Circle2D *circle1,*circle2,*circle3;
//Polygon2D *triangle1, *triangle2, *triangle3, *triangle4;
//Polygon2D *triangle5, *triangle6, *triangle7, *triangle8,*little;
//Rectangle2D *rect1,*rect2,*rect3,*testrect;
//Line2D *line1, *line2, *line3, *line4;
Spaceship *ship;

ostringstream convert;

Rectangle2D *contur;

Text *lives_text, *score_text, *gametime_text,*game_text;
vector<bool> priority;

string lives;
string score_string;
string time_string;
string game_over;

Point2D *tempCenter;

int score = 0;
int lives_counter = 10;

vector<Enemy*> enemies;
//vector<Enemy*> enemies;
Enemy *enemy;

float sinu;
float cosu;

//float u;

void activateWeapon(){
	if (ship->activated == false){
		DrawingWindow::addObject2D_to_Visual2D(ship->weapon, v2d1);
		ship->activateWeapon();
		Transform2D::loadIdentityMatrix();
		Transform2D::translateMatrix(-ship->b, -ship->c);
		Transform2D::rotateMatrix(ship->angle);
		Transform2D::translateMatrix(ship->b + ship->tx, ship->c + ship->ty);
		Transform2D::applyTransform(ship->weapon);
	}
}

void deactivateWeapon(){
	if (ship->activated == true){
		DrawingWindow::removeObject2D_from_Visual2D(ship->weapon, v2d1);
		ship->deactivateWeapon();
	}
}

//indica in ce cadran se afla nava
//	I		II
//	III		IV
int cadranNava(){

	int width = DrawingWindow::width;
	int heigth = DrawingWindow::height;
	int x = ship->center->x + ship->tx;
	int y = ship->center->y + ship->ty;

	if (x < width / 2 && y > heigth / 2)
		return 1;
	if (x >= width / 2 && y > heigth / 2)
		return 2;
	if (x <= width / 2 && y <= heigth / 2)
		return 3;

	return 4;

}

//va fi coltul opus cadranului navei
int cadranInamic(int cadranNava){

	switch (cadranNava)
	{
	case 1:{
			   return 4;
	};
		break;

	case 2:{
			   return 3;
	};
		break;

	case 3:{
			   return 2;
	};
		break;

	case 4:{
			   return 1;
	};
		break;


	default:
		return -1;
		break;
	}

}

int help;
int nr_cicles = 0;
//in ce cadran al arenei va aparea inamicul
void generateEnemie(int cadran){


	Point2D* center;

	final = clock() - start;
	time_counter += (float)final / CLOCKS_PER_SEC;


	help = (int)(time_counter / 1000)-nr_cicles*15;

	//cout << (int)(time_counter / 1000) << endl;

	float standardx = 100;
	float standardy = 100;

	float local_width = DrawingWindow::width;
	float local_height = DrawingWindow::height;


	switch (cadran)
	{
	case 1:{
			   center = new Point2D(standardx, standardy+local_height/2);
	};
		break;

	case 2:{
			   center = new Point2D(standardx + local_width / 2, standardy + local_height / 2);
	};
		break;

	case 3:{
			   center = new Point2D(standardx, standardy);
	};
		break;

	//case 4:{
		//	   center = new Point2D(standardx + local_width / 2, standardy);
	//};
		//break;


	default:{
				center = new Point2D(standardx + local_width / 2, standardy);
	};
		break;
	}


	if (help % 10 == 0 && help != 0){
			int type = rand() % 3 + 1;
			nr_cicles++;
		//int type = rand() % 3;
			enemy = new Enemy(type, v2d1, center);
			enemy->cadru = cadran;
			enemy->setBounds(upperLimit, lowerLimit, leftLimit, rightLimit);
			DrawingWindow::addObject2D_to_Visual2D(enemy, v2d1);
			enemies.push_back(enemy);
			priority.push_back(true);
	}
}

//nu tine cont de timp
void generateEnemieTimeless(int cadran){


	Point2D* center;

	//final = clock() - start;
	//time_counter += (float)final / CLOCKS_PER_SEC;


	//help = (int)(time_counter / 1000) - nr_cicles * 15;

	//cout << (int)(time_counter / 1000) << endl;

	float standardx = 100;
	float standardy = 100;

	float local_width = DrawingWindow::width;
	float local_height = DrawingWindow::height;


	switch (cadran)
	{
	case 1:{
			   center = new Point2D(standardx, standardy + local_height / 2);
	};
		break;

	case 2:{
			   center = new Point2D(standardx + local_width / 2, standardy + local_height / 2);
	};
		break;

	case 3:{
			   center = new Point2D(standardx, standardy);
	};
		break;

		//case 4:{
		//	   center = new Point2D(standardx + local_width / 2, standardy);
		//};
		//break;


	default:{
				center = new Point2D(standardx + local_width / 2, standardy);
	};
		break;
	}


	//if (help % 15 == 0 && help != 0){
		//int type = rand() % 3 + 1;
		//nr_cicles++;
		int type = rand() % 3;
		enemy = new Enemy(type, v2d1, center);
		enemy->cadru = cadran;
		enemy->setBounds(upperLimit, lowerLimit, leftLimit, rightLimit);
		DrawingWindow::addObject2D_to_Visual2D(enemy, v2d1);
		enemies.push_back(enemy);
		priority.push_back(true);
	//}
}

void startingEnemies(){
	
	

	for (int i = 0; i < 4; i++)
		generateEnemieTimeless(i+1);

}

//functia care permite adaugarea de obiecte
void DrawingWindow::init()
{
	//for (int i = 0; i < enemies.size(); i++)
		//cout << i << endl;
	start = clock();



	v2d1 = new Visual2D(0, 0, DrawingWindow::width, DrawingWindow::height, 0, DrawingWindow::height/6, DrawingWindow::width, DrawingWindow::height);
	//v2d1->tipTran(true);
	addVisual2D(v2d1);
	//v2d1->tipTran(true);

	v2d2 = new Visual2D(0, 0, DrawingWindow::width, DrawingWindow::height, 0, 0, DrawingWindow::width, DrawingWindow::height/6);
	//v2d2->tipTran(true);
	addVisual2D(v2d2);
	
	ship = new Spaceship();
	ship->setBounds(upperLimit, lowerLimit, leftLimit, rightLimit);
	//addObject2D(ship);

	//for debug
	//cout << ship->lineEquation(200,new Point2D(100,100),new Point2D(400,400))<<endl;
	//cout << ship->lineEquation(357.25, new Point2D(400, 500), new Point2D(355, 1549/3)) << endl;

	ship->generateWeaponPoints();

	addObject2D_to_Visual2D(ship, v2d1);
	
	startingEnemies();


	Line2D *test = new Line2D(Point2D(0, 0.01), Point2D(DrawingWindow::width, 0.01), Color(0, 1, 1));
	addObject2D_to_Visual2D(test, v2d2);

	/*
	score_string.append("Time : ");
	convert << time_counter;
	score_string.append(convert.str());
	convert.str("");
	convert.clear();
	gametime_text = new Text(time_string, Point2D(300, 100), Color(0, 0, 1), BITMAP_TIMES_ROMAN_24);
	DrawingWindow::addText_to_Visual2D(gametime_text, v2d2);
	time_string.clear();
	*/

	//ASTA E PARTEA DE SUS CU TEXTUL
	//APOI FAC O CLASA SPECIAL PT EA
	//lifes = new Text("Remaining lifes : ", Point2D(0, 50), Color(0, 0, 1), BITMAP_TIMES_ROMAN_24);
	lives.append("Remaining lives : ");
	convert << ship->lives_counter;
	lives.append(convert.str());
	convert.str("");
	convert.clear();
	lives_text = new Text(lives, Point2D(50, 100), Color(0, 0, 1), BITMAP_TIMES_ROMAN_24);
	DrawingWindow::addText_to_Visual2D(lives_text, v2d2);
	lives.clear();


	//score = new Text("SCORE : ", Point2D(700, 50), Color(0, 0, 1), BITMAP_TIMES_ROMAN_24);
	score_string.append("Score : ");

	convert << score;
	score_string.append(convert.str());
	convert.str("");
	convert.clear();
	score_text = new Text(score_string, Point2D(700, 100), Color(0, 0, 1), BITMAP_TIMES_ROMAN_24);
	DrawingWindow::addText_to_Visual2D(score_text, v2d2);
	score_string.clear();

	//background
	rect = new Rectangle2D(Point2D(0, 0), DrawingWindow::width, DrawingWindow::height, Color(0, 0, 0), true);
	addObject2D(rect);

}



void updateLives(){

	//if (ship->collided == true){
		DrawingWindow::removeText_from_Visual2D(lives_text, v2d2);
		lives.append("Remaining lives : ");
		//convert << ship->lives_counter;
		convert << lives_counter;
		lives.append(convert.str());
		convert.str("");
		convert.clear();
		lives_text = new Text(lives, Point2D(50, 100), Color(0, 0, 1), BITMAP_TIMES_ROMAN_24);
		DrawingWindow::addText_to_Visual2D(lives_text, v2d2);
		lives.clear();
	//}


}

void updateScore(){

	DrawingWindow::removeText_from_Visual2D(score_text, v2d2);
	score_string.append("Score : ");
	convert << totalScore;
	score_string.append(convert.str());
	convert.str("");
	convert.clear();
	score_text = new Text(score_string, Point2D(700, 100), Color(0, 0, 1), BITMAP_TIMES_ROMAN_24);
	DrawingWindow::addText_to_Visual2D(score_text, v2d2);
	score_string.clear();


}
/*
void updateTime(){
	
	final = clock() - start;
	time_counter += (float)final / CLOCKS_PER_SEC;
	
	cout << (int)time_counter/1000<<endl;
	//time_counter++;
	DrawingWindow::removeText_from_Visual2D(gametime_text, v2d2);
	score_string.append("Time : ");
	convert << time_counter;
	score_string.append(convert.str());
	convert.str("");
	convert.clear();
	gametime_text = new Text(time_string, Point2D(350, 100), Color(0, 0, 1), BITMAP_TIMES_ROMAN_24);
	DrawingWindow::addText_to_Visual2D(gametime_text, v2d2);
	time_string.clear();
}
*/

//"simulez" trecerea timpului
void Timer(){

	final = clock() - start;
	time_counter += (float)final / CLOCKS_PER_SEC;

	cout << (int)(time_counter / 1000) << endl;
}

float distance(Point2D* a, Point2D* b){

	return sqrt(pow(a->x - b->x, 2) + pow(a->y - b->y, 2));
}

//int help1 = 0;
//int help2 = 0;
float aux;
float auy;
Point2D* au;
//functia care permite animatia
void DrawingWindow::onIdle()
{


	//updateTime();
	//Timer();
	
	generateEnemie(cadranInamic(cadranNava()));

	//generateEnemie((rand() % 4) + 1);

	for (int i = 0; i < enemies.size(); i++){
		{
			//if (ship->activated);
				//enemies[i]->weaponCollision(ship->tranformWeaponPoints());

			switch (enemies[i]->type)
			{
				case 1:{
						   enemies[i]->angle += enemies[i]->rotate_speed;
						   //enemies[i]->tx += enemies[i]->translate_speed;
						   //enemies[i]->ty += enemies[i]->translate_speed;

						   enemies[i]->tx += enemies[i]->translate_speed;
						   enemies[i]->ty += enemies[i]->translate_speed;

						   enemies[i]->isOut();

						   if (enemies[i]->outOfBounds == false){
							   enemies[i]->tx = enemies[i]->tx;
							   enemies[i]->ty = enemies[i]->ty;
						   }
						   else{

							   enemies[i]->tx -= 2*enemies[i]->translate_speed;
							   enemies[i]->ty -= 2*enemies[i]->translate_speed;
						   }

						//   if (ship->activated){
						//	   enemies[i]->weaponCollision(ship->tranformWeaponPoints());
						 //  }

						  // ship->collisionDetection(enemies[i]);

						   if (enemies[i]->alreadyColided == false){
							   // ship->collisionDetection(enemies[i]);
							   aux = ship->center->x + ship->tx;
							   auy = ship->center->y + ship->ty;

							   au = new Point2D(aux, auy);

							   aux = enemies[i]->center->x + enemies[i]->tx;
							   auy = enemies[i]->center->y + enemies[i]->ty;

							   if (distance(au, new Point2D(aux, auy)) <= (ship->radius + enemies[i]->radius)){

								   //if (enemy->collisionID == 0){
								   lives_counter--;
								   cout << lives_counter << endl;
								   //}

								   //enemy->collisionID = 1;

								   if (lives_counter <= 0)
									   lives_counter = 0;

								   enemies[i]->alreadyColided = true;
								    updateLives();

							   }
						   }


						   if (enemies[i]->letItLive == true){

							  // enemies[i]->trajectory();
							   Transform2D::loadIdentityMatrix();
							   Transform2D::translateMatrix(-enemies[i]->center->x, -enemies[i]->center->y);
							   Transform2D::rotateMatrix(enemies[i]->angle);
							   Transform2D::translateMatrix(enemies[i]->center->x+enemies[i]->tx, enemies[i]->center->y+enemies[i]->ty);
							   
							   enemies[i]->applyTranform();


						   }
						   else{
							   totalScore += enemies[i]->score;
							   DrawingWindow::removeEnemy_from_Visual2D(enemies[i], v2d1);
							   enemies.erase(enemies.begin() + i);
							   updateScore();
						   }

				};
					break;

				case 2:{
						   enemies[i]->angle += enemies[i]->rotate_speed;

						   enemies[i]->isOut();
						   if (enemies[i]->outOfBounds == false){

							   sinu = (ship->center->x + ship->tx + ship->translate_speed*cos(ship->angle) - enemies[i]->center->x) / distance(ship->center, enemies[i]->center);
							   cosu = (ship->center->y + ship->translate_speed*sin(ship->angle)+ship->ty - enemies[i]->center->y) / distance(ship->center, enemies[i]->center);

							   enemies[i]->tx += enemies[i]->translate_speed*sinu;
							   enemies[i]->ty += enemies[i]->translate_speed*cosu;

						   }


						   

						  if (enemies[i]->alreadyColided == false){
							  // ship->collisionDetection(enemies[i]);
							  aux = ship->center->x + ship->tx;
							  auy = ship->center->y + ship->ty;

							  au = new Point2D(aux, auy);

							  aux = enemies[i]->center->x + enemies[i]->tx;
							  auy = enemies[i]->center->y + enemies[i]->ty;

							  if (distance(au, new Point2D(aux, auy)) <= (ship->radius + enemies[i]->radius)){

								  //if (enemy->collisionID == 0){
								  lives_counter--;
								  cout << lives_counter << endl;
								  //}

								  //enemy->collisionID = 1;

								  if (lives_counter <= 0)
									  lives_counter = 0;

								  enemies[i]->alreadyColided = true;
								  updateLives();

							  }
						  }

						   if (enemies[i]->letItLive == true){
							   
				

							   Transform2D::loadIdentityMatrix();
							   Transform2D::translateMatrix(-enemies[i]->center->x, -enemies[i]->center->y);
							   Transform2D::rotateMatrix(enemies[i]->angle);
							   Transform2D::translateMatrix(enemies[i]->center->x+enemies[i]->tx, enemies[i]->center->y+enemies[i]->ty);
							   enemies[i]->applyTranform();


						   }
						   else{
							   totalScore += enemies[i]->score;
							   DrawingWindow::removeEnemy_from_Visual2D(enemies[i], v2d1);
							   enemies.erase(enemies.begin() + i);
							   updateScore();
						   }

				}
					break;
				
				case 3:{
						   enemies[i]->angle += enemies[i]->rotate_speed;


						   //ship->tx -= ship->translate_speed*cos(ship->angle);
						   //ship->ty -= ship->translate_speed*sin(ship->angle);
						   enemies[i]->tx += enemies[i]->translate_speed*cos(ship->angle);
						   enemies[i]->ty += enemies[i]->translate_speed*sin(ship->angle);
						   enemies[i]->isOut();





						   if (enemies[i]->alreadyColided == false){
							   // ship->collisionDetection(enemies[i]);
							   aux = ship->center->x + ship->tx;
							   auy = ship->center->y + ship->ty;

							   au = new Point2D(aux, auy);

							   aux = enemies[i]->center->x + enemies[i]->tx;
							   auy = enemies[i]->center->y + enemies[i]->ty;

							   if (distance(au, new Point2D(aux, auy)) <= (ship->radius + enemies[i]->radius)){

								   //if (enemy->collisionID == 0){
								   lives_counter--;
								   cout << lives_counter << endl;
								   //}

								   //enemy->collisionID = 1;

								   if (lives_counter <= 0)
									   lives_counter = 0;

								   enemies[i]->alreadyColided = true;
								    updateLives();

							   }
						   }

						   if (enemies[i]->letItLive == true){


							   if (enemies[i]->outOfBounds == true){

								   enemies[i]->tx -= enemies[i]->translate_speed*cos(ship->angle);
								   enemies[i]->ty -= enemies[i]->translate_speed*sin(ship->angle);

								   Transform2D::loadIdentityMatrix();
								   Transform2D::translateMatrix(-enemies[i]->center->x, -enemies[i]->center->y);
								   Transform2D::rotateMatrix(enemies[i]->angle);
								   Transform2D::translateMatrix(enemies[i]->center->x+enemies[i]->tx, enemies[i]->center->y+enemies[i]->ty);

							   }
							   else{

								   Transform2D::loadIdentityMatrix();
								   Transform2D::translateMatrix(-enemies[i]->center->x, -enemies[i]->center->y);
								   Transform2D::rotateMatrix(enemies[i]->angle);
								   Transform2D::translateMatrix(enemies[i]->center->x + enemies[i]->tx, enemies[i]->center->y + enemies[i]->ty);

							   }
							   
								enemies[i]->applyTranform();


						   }
						   else{
							   totalScore += enemies[i]->score;
							   DrawingWindow::removeEnemy_from_Visual2D(enemies[i], v2d1);
							   enemies.erase(enemies.begin() + i);
							   updateScore();
						   }

				}
					break;

				default:
					//updateLives();
					break;
			}


 		}
	}
	
	if (lives_counter == 0){
		
		game_over.append("GAME OVER");
		game_text = new Text(game_over, Point2D(400, 100), Color(0, 1, 1), BITMAP_TIMES_ROMAN_24);
		DrawingWindow::addText_to_Visual2D(game_text, v2d2);
		game_over.clear();
	}

}

//functia care se apeleaza la redimensionarea ferestrei
void DrawingWindow::onReshape(int width,int height)
{
	
	v2d2->poarta(0, 0, width, height / 6);
	v2d1->poarta(0 ,height/6,width,height); 
	
	
}

vector<Point2D*> weaponPoints;
Point2D *temp = new Point2D();

float tempx;
float tempy;

//functia care defineste ce se intampla cand se apasa pe tastatura
void DrawingWindow::onKey(unsigned char key)
{

	//aici verific daca face bum inamicul sau nu
	//verific la fiecare miscare
	switch(key)
	{

		case KEY_UP: {

						 ship->isOut();
						 if (ship->outOfBounds == false){

							 ship->tx += ship->translate_speed*cos(ship->angle);
							 ship->ty += ship->translate_speed*sin(ship->angle);
							 //ship->outOfBounds == true;
						 }

						// for (int i = 0; i < enemies.size(); i++){
							// ship->collisionDetection(enemies[i]);
							// if (ship->collided = true)
								// enemies[i]->collisionID = 1;
						// }
						 //updateLives();
						 //pt fiecare tip de transformare voi aplica
						 //tempCenter = new Point2D(ship->center->x, ship->center->y);
						 updateLives();

						 Transform2D::loadIdentityMatrix(); 
						 Transform2D::translateMatrix(-ship->b, -ship->c);
						 Transform2D::rotateMatrix(ship->angle);
						 Transform2D::translateMatrix(ship->b + ship->tx, ship->c + ship->ty);

						 if (ship->activated == true){
						
							 for (int i = 0; i < ship->weaponPoints.size(); i++){

								 //tempx = weaponPoints[i]->x + ship->tx ;
								 //tempy = weaponPoints[i]->y + ship->ty ;
								 Transform2D::applyTransform(ship->weaponPoints[i],temp);
								 //weaponPoints.push_back(new Point2D(tempx,tempy));
								 weaponPoints.push_back(temp);
							 }

							 Transform2D::applyTransform(ship->weapon);

							 for (int i = 0; i < enemies.size();i++)
							 	   enemies[i]->weaponCollision(weaponPoints);
							 
						 }

							ship->applyTranform();


						


		};
			break;
		case KEY_LEFT:{
	

							  ship->angle += ship->rotate_speed;


							 // for (int i = 0; i < enemies.size(); i++)
								//  ship->collisionDetection(enemies[i]);

							  //updateLives();

						  //ship->angle += ship->rotate_speed;

						  Transform2D::loadIdentityMatrix();
						  Transform2D::translateMatrix(-ship->b, -ship->c);
						  Transform2D::rotateMatrix(ship->angle);
						  Transform2D::translateMatrix(ship->b+ship->tx, ship->c+ship->ty);
						 
						  //if (ship->activated == true)
							//  Transform2D::applyTransform(ship->weapon);

						  if (ship->activated == true){

							  for (int i = 0; i < ship->weaponPoints.size(); i++){

								  //tempx = weaponPoints[i]->x + ship->tx ;
								  //tempy = weaponPoints[i]->y + ship->ty ;
								  Transform2D::applyTransform(ship->weaponPoints[i], temp);
								  //weaponPoints.push_back(new Point2D(tempx,tempy));
								  weaponPoints.push_back(temp);
							  }

							  Transform2D::applyTransform(ship->weapon);

							  for (int i = 0; i < enemies.size(); i++)
								  enemies[i]->weaponCollision(weaponPoints);

						  }

						  ship->applyTranform();
						
					


		};
			break;
		case KEY_RIGHT:{
						 

						   ship->angle -= ship->rotate_speed;

						   for (int i = 0; i < enemies.size(); i++){
							   ship->collisionDetection(enemies[i]);
							   if (ship->collided == true){
								   enemies[i]->collisionID = i+1;
							   }
						   }
						   

						   //updateLives();
						   

						   Transform2D::loadIdentityMatrix();
						   Transform2D::translateMatrix(-ship->b, -ship->c);
						   Transform2D::rotateMatrix(ship->angle);
						   Transform2D::translateMatrix(ship->b+ship->tx, ship->c+ship->ty);

						  // if (ship->activated == true)
							//   Transform2D::applyTransform(ship->weapon);
								

						   if (ship->activated == true){

							   for (int i = 0; i < ship->weaponPoints.size(); i++){

								   //tempx = weaponPoints[i]->x + ship->tx ;
								   //tempy = weaponPoints[i]->y + ship->ty ;
								   Transform2D::applyTransform(ship->weaponPoints[i], temp);
								   //weaponPoints.push_back(new Point2D(tempx,tempy));
								   weaponPoints.push_back(temp);
							   }

							   Transform2D::applyTransform(ship->weapon);

							   for (int i = 0; i < enemies.size(); i++)
								   enemies[i]->weaponCollision(weaponPoints);

						   }

						   ship->applyTranform();
							
		};
			break;

		case KEY_DOWN:{
						  ship->isOut();
						  if (ship->outOfBounds == false){

							  ship->tx -= ship->translate_speed*cos(ship->angle);
							  ship->ty -= ship->translate_speed*sin(ship->angle);
							  //ship->outOfBounds == true;
						  }

						  for (int i = 0; i < enemies.size(); i++)
							  ship->collisionDetection(enemies[i]);
						  updateLives();






						  Transform2D::loadIdentityMatrix();
						  Transform2D::translateMatrix(-ship->b, -ship->c);
						  Transform2D::rotateMatrix(ship->angle);
						  Transform2D::translateMatrix(ship->b + ship->tx, ship->c + ship->ty);

						  if (ship->activated == true)
							  Transform2D::applyTransform(ship->weapon);


						  ship->applyTranform();
		};
			break;

		case 'a':{
					 activateWeapon();

		};
			break;

		case 'z':{
					 deactivateWeapon();

		};
			break;
	}

}

//functia care defineste ce se intampla cand se da click pe mouse
void DrawingWindow::onMouse(int button,int state,int x, int y)
{
	
}


int main(int argc, char** argv)
{

	

	//creare fereastra
	DrawingWindow dw(argc, argv, 1024, 720, 0, 0, "Tema EGC");
	//se apeleaza functia init() - in care s-au adaugat obiecte
	dw.init();
	//se intra in bucla principala de desenare - care face posibila desenarea, animatia si procesarea evenimentelor
	//updateTime();
	//Timer();
	dw.run();
	return 0;

}