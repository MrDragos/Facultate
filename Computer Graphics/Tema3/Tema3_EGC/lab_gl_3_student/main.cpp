//-------------------------------------------------------------------------------------------------
// Descriere: fisier main
//
// Autor: Sava Dragos
// Grupa: 334CA
//-------------------------------------------------------------------------------------------------

#include "MYTrack.h"


//incarcator de meshe
#include "lab_mesh_loader.hpp"

//geometrie: drawSolidCube, drawWireTeapot...
#include "lab_geometry.hpp"

//incarcator de shadere
#include "lab_shader_loader.hpp"

//interfata cu glut, ne ofera fereastra, input, context opengl
#include "lab_glut.hpp"

//camera
#include "lab_camera.hpp"

//time
#include <ctime>

#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>


//#include "MyObj.h"

//#define PI 3.14

using namespace std;






class Laborator : public lab::glut::WindowListener{

//variabile
private:

	MyTrack *track;
	glm::mat4 model_matrix, projection_matrix,view_matrix;		//matrici 4x4 pt modelare vizualizare proiectie
	lab::Camera TV_camera;
	lab::Camera FP_player1;
	lab::Camera FP_player2;
	lab::Camera FP_player3;
	lab::Camera TP_player1;
	lab::Camera TP_player2;
	lab::Camera TP_player3;

	unsigned int gl_program_shader;		//id-ul de opengl al obiectului de tip program shader
	unsigned int screen_width, screen_height;


	bool tv = false;
	bool FP1 = false;
	bool FP2 = true;
	bool FP3 = false;
	bool TP1 = false;

	//unsigned int mesh_vbo, mesh_ibo, mesh_vao, mesh_num_indices;
	unsigned int p1_vbo, p1_ibo, p1_vao, p1_num_indices;
	unsigned int p2_vbo, p2_ibo, p2_vao, p2_num_indices;
	unsigned int p3_vbo, p3_ibo, p3_vao, p3_num_indices;
	unsigned int camera_vbo, camera_ibo, camera_vao, camera_num_indices;
	 
	float angle = 0;	//unghi rotatie
	float speed = 0.5; //viteza de deplasare
	float radius;
	float w;	// latime banda	

	float forward;
	float up;
	float right;



	//pentru jucatorul 2
	float tx_ch = 0;
	float ty_ch = 0;
	float tz_ch = 0;
	float angle_ch = 0;
	//float speed_ch = 0.1;
	float cam_tx = 0;
	float cam_tz = 0;

	int stop2 = 0;
	int stop3 = 0;

	bool p1_left = false;
	bool p1_right = false;
	bool p1_up = false;
	bool p1_down = false;

	int cadran;

	float alpha = 0;
	float alpha1 = 0;
	float alpha3 = 0;
	float theta3 = 270;
	//float theta = 0;
	float theta = 270;
	//int cadran;
	float k = 0;	//o constanta/coeficient

	//end jucator 2

	float p1_radius;	//raza cercului pistei pe care se deplaseaza jucatorul 1
	float cam_radius;   //raza cercului pistei imaginae pe care se plimba camera

	

//metode
public:


	//Color color;
	//MyObj ironMan;
	//MyObj batMan;
	//MyObj superThunderDick;
    float track_length = 200;
	float tx = 0;	//translatie pe Ox
	//float tx = radius + w / 2;
	float tz = 0;	//translatie pe Oz
	glm::vec3 p2_poz;
	glm::vec3 p3_poz;
	glm::vec3 p1_poz;
	glm::vec3 cam_poz;

	float start_x;
	float start_y;
	float start_z;

	float speed1;
	float speed2;
	float speed3;

	float original_speed;	//aici voi salva viteza originala a jucatorului 1

	string players[6];

    

	int id_player1;
	int id_player2;
	int id_player3;


	float scale_factor1;
	float scale_factor2;
	float scale_factor3;

	float scaleFactors[6];

	void setScaleFactors(){
		scale_factor1 = scaleFactors[id_player1-1];
		scale_factor2 = scaleFactors[id_player2 - 1];
		scale_factor3 = scaleFactors[id_player3 - 1];
	}

	//constructor .. e apelat cand e instantiata clasa
	Laborator(){

		players[0] = "resurse\\Dragonite.obj";
		players[1] = "resurse\\Charizard.obj";
		players[2] = "resurse\\Charmander.obj";
		players[3] = "resurse\\Ponyta.obj";
		players[4] = "resurse\\Raichu.obj";
		players[5] = "resurse\\Scyther.obj";


		scaleFactors[0] = 0.25;
		scaleFactors[1] = 0.3;
		scaleFactors[2] = 0.9;
		scaleFactors[3] = 0.4;
		scaleFactors[4] = 0.4;
		scaleFactors[5] = 0.25;

		//citesc vitezele din fisier pentru fiecare jucator
		string line;
		ifstream myfile("Speed.txt");

		getline(myfile, line);
		speed1 = atof(line.c_str());
		line.clear();
		cout << speed1 << endl;

		original_speed = speed1;

		getline(myfile, line);
		speed2 = atof(line.c_str());
		line.clear();
		cout << speed2 << endl;

		getline(myfile, line);
		speed3 = atof(line.c_str());
		line.clear();
		cout << speed3 << endl;

		//setari pentru desenare, clear color seteaza culoarea de clear pentru ecran (format R,G,B,A)
		glClearColor(0.5,0.5,0.5,1);
		glClearDepth(1);			//clear depth si depth test (nu le studiem momentan, dar avem nevoie de ele!)
		glEnable(GL_DEPTH_TEST);	//sunt folosite pentru a determina obiectele cele mai apropiate de camera (la curs: algoritmul pictorului, algoritmul zbuffer)
		
		//incarca un shader din fisiere si gaseste locatiile matricilor relativ la programul creat
		gl_program_shader = lab::loadShader("shadere\\shader_vertex.glsl", "shadere\\shader_fragment.glsl");



		cout << endl << "Puteti alege dintre urmatorii jucatori : " << endl;

		cout << " 1    -    " << "Dragonite" <<endl;
		cout << " 2    -    " << "Charizard" << endl;;
		cout << " 3    -    " << "Charmander" << endl;
		cout << " 4    -    " << "Ponyta" << endl;
		cout << " 5    -    " << "Raichu" << endl;
		cout << " 6    -    " << "Scyther" << endl;
		
		cout << endl;

		cout << "Alegeti primul jucator : ";
		cin >> id_player1;
		cout << "Alegeti al doilea jucator : ";
		cin >> id_player2;
		cout << "Alegeti al treilea jucator : ";
		cin >> id_player3;


		setScaleFactors();

		lab::loadObj(players[id_player1-1], p1_vao, p1_vbo, p1_ibo, p1_num_indices, id_player1);
		lab::loadObj(players[id_player2-1], p2_vao, p2_vbo, p2_ibo, p2_num_indices, id_player2);
		lab::loadObj(players[id_player3-1], p3_vao, p3_vbo, p3_ibo, p3_num_indices, id_player3);

		lab::loadObj("resurse\\camera.obj", camera_vao, camera_vbo, camera_ibo, camera_num_indices, 7);

		track = new MyTrack(track_length, 30, glm::vec3(0.5, 0.5, 0.5));

		radius = 30;
		w = 10;

		tx = 0;

		//pozitiile de inceput
		start_x = radius + w / 2;
		start_y = 0;
		start_z = -track_length / 2;


		//pozitii jucator 2 
		p2_poz.x = start_x+w;
		p2_poz.y = 0;
		p2_poz.z = start_z;


		//pozitii jucator 3
		p3_poz.x = start_x+2*w;
		p3_poz.y = 0;
		p3_poz.z = start_z;


		//pozitii jucator 1
		p1_poz.x = start_x;
		p1_poz.y = 0;
		p1_poz.z = -track_length/2;

		//pozitii camera
		cam_poz.x = p1_poz.x+5*w/2+10;
		cam_poz.y = 30;
		cam_poz.z = -track_length/2;


		//matrici de modelare si vizualizare
		model_matrix = glm::mat4(1,0,0,0, 0,1,0,0, 0,0,1,0, 0,0,0,1);

		//setez camerele
		TV_camera.set(glm::vec3(0, 250, -track_length/2), glm::vec3(0, 0, 0), glm::vec3(0, 1, 0));
		TP_player1.set(glm::vec3(radius+3*w+50, 50, -track_length / 2), glm::vec3(0, 0, 0), glm::vec3(0, 1, 0));
		FP_player1.set(glm::vec3(p1_poz.x, 10, p1_poz.z), glm::vec3(p1_poz.x, 0, p1_poz.z + 10), glm::vec3(0, 1, 0));
		FP_player2.set(glm::vec3(p2_poz.x, 10, p2_poz.z), glm::vec3(p2_poz.x, 10, p2_poz.z+10), glm::vec3(0, 1, 0));
		FP_player3.set(glm::vec3(p3_poz.x, 10, p3_poz.z), glm::vec3(p3_poz.x, 10, p3_poz.z + 10), glm::vec3(0, 1, 0));
		
		//desenare wireframe
		//glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		//glLineWidth(10);
		//glPointSize(10);

		//raza de la origine la jucator 1
		p1_radius = radius + w / 2;
		//raza de la origine la camera
		cam_radius = radius + 3 * w + 10;

	}

	//destructor .. e apelat cand e distrusa clasa
	~Laborator(){
		//distruge shader
		glDeleteProgram(gl_program_shader);

		//distruge mesh incarcat
		delete track;
	}
	
	//--------------------------------------------------------------------------------------------
	//functii de cadru ---------------------------------------------------------------------------

	//functie chemata inainte de a incepe cadrul de desenare, o folosim ca sa updatam situatia scenei ( modelam/simulam scena)
	void notifyBeginFrame(){

	}

	//imi intoarce cadranul in care se afla jucatorul
	//in functie de Ox si Oz
	// 1 ++
	// 2 -+
	// 3 --
	// 4 +-
	static int getSector(float pos_x,float pos_z){

		return (pos_x > 0) ? ((pos_z > 0) ? 1 : 4) : ((pos_z > 0) ? 2 : 3);

	}

	//1 pentru primul semicerc
	//2 pentru al doilea semicerc
	//3 pentru culoarul drept de unde pornesc
	//4 pentru celalalt culoar
	//"culoar" = zona dreapta
	static int getZone(float pos_x, float pos_z,float track_length){

		if (pos_z >= 0){
			return 1;
		}
		else if (pos_z < -track_length ){
			return 2;
		}
		else if (pos_x > 0){
			return 3;
		}
		else{
			return 4;
		}

	}


	//verifica daca jucatorul 1 a iesit din teren

	bool isOut(){

		int zona;
		bool value;
		float temp_radius = radius + w / 2;

		zona = getZone(p1_poz.x, p1_poz.z,track_length);

		switch (zona)
		{
		case 1:{
				   if (pow(p1_poz.x, 2) + pow(p1_poz.z, 2) < pow(radius, 2))
					   value = true;
				   else if (pow(p1_poz.x, 2) + pow(p1_poz.z, 2) > pow(radius + 3 * w, 2))
					   value = true;
				   else
					   value = false;;
		};
			break;
		case 2:{

				   if (pow(p1_poz.x, 2) + pow(p1_poz.z + track_length, 2) < pow(radius, 2))
					   value = true;
				   else if (pow(p1_poz.x, 2) + pow(p1_poz.z + track_length, 2) > pow(radius+3*w, 2))
					   value = true;
				   else
					   value = false;;
		}
			break;
		case 3:{
				   if (p1_poz.x < radius || p1_poz.x > radius + 3 * w)
					   value = true;
				   else
					   value = false;;
		}
			break;
		case 4:{
				   if (p1_poz.x > -radius || p1_poz.x < -radius - 3 * w)
					   value = true;
				   else
					   value = false;;
		};
			break;

		default:
			break;
		};

		return value;

	}

	//float tz = 0;

	int j = 0;
	//functia de afisare (lucram cu banda grafica)
	void notifyDisplayFrame(){
		
		//pe tot ecranul
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		//cadru normal
		{
			glViewport(0,0, screen_width, screen_height);
			
			//foloseste shaderul
			glUseProgram(gl_program_shader);
				
			if (tv){

				//trimite variabile uniforme la shader
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(model_matrix));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "view_matrix"), 1, false, glm::value_ptr(TV_camera.getViewMatrix()));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "projection_matrix"), 1, false, glm::value_ptr(projection_matrix));

			}
			else if(TP1){
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(model_matrix));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "view_matrix"), 1, false, glm::value_ptr(TP_player1.getViewMatrix()));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "projection_matrix"), 1, false, glm::value_ptr(projection_matrix));
			}
			else if (FP2){
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(model_matrix));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "view_matrix"), 1, false, glm::value_ptr(FP_player2.getViewMatrix()));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "projection_matrix"), 1, false, glm::value_ptr(projection_matrix));
			}
			else if (FP1){
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(model_matrix));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "view_matrix"), 1, false, glm::value_ptr(FP_player1.getViewMatrix()));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "projection_matrix"), 1, false, glm::value_ptr(projection_matrix));
			}
			else if (FP3){
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(model_matrix));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "view_matrix"), 1, false, glm::value_ptr(FP_player3.getViewMatrix()));
				glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "projection_matrix"), 1, false, glm::value_ptr(projection_matrix));
			}

			//bind obiect
			track->BindVertexArray();


			p1_poz.x = tx + radius + w / 2;
			p1_poz.z = tz - track_length / 2;


			if (p1_up) {

				tx += speed1*sin(angle);
				tz += speed1*cos(angle);
				
				cam_tx += speed1*(cam_radius / p1_radius)*sin(angle);
				cam_tz += speed1*(cam_radius / p1_radius)*cos(angle);

			}


			if (p1_down) {


				tx -= speed1 * sin(angle);
				tz -= speed1 * cos(angle);

				cam_tx -= speed1*(cam_radius / p1_radius)*sin(angle);
				cam_tz -= speed1*(cam_radius / p1_radius)*cos(angle);

			}
			if (p1_right) {
				angle -= 0.025;
				alpha1 += 0.025;
			}
			if (p1_left ) { 
				angle += 0.025; 
				alpha1 -= 0.025;

			}

			cadran = getZone(p1_poz.x, p1_poz.z, track_length);

			cam_poz.x = cam_tx + radius + 3 * w + 10;
			cam_poz.z = cam_tz - track_length / 2;

			FP_player1.set(glm::vec3(p1_poz.x, 10, p1_poz.z), glm::vec3(p1_poz.x + cos(alpha1 + PI / 2) * 10, 10, p1_poz.z + sin(alpha1 + PI / 2) * 10), glm::vec3(0, 1, 0));

			switch (cadran)
			{
				case 1:
					TP_player1.set(glm::vec3(cam_poz.x, cam_poz.y, cam_poz.z), glm::vec3(0, 0, 0), glm::vec3(0, 1, 0));
					break;
				case 2:
					
					TP_player1.set(glm::vec3(cam_poz.x, cam_poz.y, cam_poz.z), glm::vec3(0, 0, -track_length), glm::vec3(0, 1, 0));
					break;
				case 3:
					
					TP_player1.set(glm::vec3(p1_poz.x + w * 5 / 2 + 10, cam_poz.y, p1_poz.z), glm::vec3(0, 0, p1_poz.z), glm::vec3(0, 1, 0));
					break;
				case 4:
					
					TP_player1.set(glm::vec3(p1_poz.x - w * 5 / 2 - 10, cam_poz.y, p1_poz.z), glm::vec3(0, 0, p1_poz.z), glm::vec3(0, 1, 0));
					break;

				default:
					break;
			}

			glm::mat4 rotate = glm::rotate(model_matrix, angle*180/(float)PI, glm::vec3(0, 1, 0));
			glm::mat4 translate = glm::translate(model_matrix, glm::vec3(p1_poz.x, 0, p1_poz.z));

			glm::mat4 scale = glm::scale(model_matrix, glm::vec3(scale_factor1, scale_factor1, scale_factor1));

			glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(translate*scale*rotate));


			glBindVertexArray(p1_vao);

			glDrawElements(GL_TRIANGLES, p1_num_indices, GL_UNSIGNED_INT, 0);

			glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(glm::mat4()));	//identity
			



			//desenez linia de finish

			glLineWidth(2.5);
			glColor3f(1.0, 0.0, 0.0);
			glBegin(GL_LINES);
			glVertex3f(radius, 0.0, 0);
			glVertex3f(radius+3*w, 0, 0);
			glEnd();


			//Miscare JUCATOR2

				if (p2_poz.z > 0){

					if (stop2 == 0){

						//alpha += 0.5;
						alpha += speed2;

						p2_poz.z = sin(alpha/180*PI)*(radius + w *3/ 2);
						p2_poz.x = cos(alpha/180*PI)*(radius + w*3 / 2);
						
						FP_player2.set(glm::vec3(p2_poz.x, 10, p2_poz.z), glm::vec3(p2_poz.x+cos((alpha+90)/180*PI)*10, 10, p2_poz.z+sin((alpha+90)/180*PI)*10), glm::vec3(0, 1, 0));
						
	
						rotate = glm::rotate(model_matrix, -alpha, glm::vec3(0, 1, 0));
	

					}
					else{

						p2_poz.x = radius + 3*w / 2;
						p2_poz.z = 0;

						alpha = 0;
						FP_player2.set(glm::vec3(p2_poz.x, 10, p2_poz.z), glm::vec3(p2_poz.x, 10, p2_poz.z + 10), glm::vec3(0, 1, 0));
					}
				}
				else if (p2_poz.x>0 && 0 >= p2_poz.z && p2_poz.z > -track_length){

					p2_poz.z += speed2;
					
					alpha = 0;
					
					//aici rezolv un mic bug
					//verific daca jucatorul 2 este de tipul 2(Charizard)
					//daca da , acesta va avea gatul mai alungit si s-ar vedea in camera capul
					//de aceea am facut acest mic artificiu in acest caz si deplasez usor camera pe Oz cu +- 4
					
					if (id_player2 == 2)
						FP_player2.set(glm::vec3(p2_poz.x, 10, p2_poz.z+4), glm::vec3(p2_poz.x, 0, p2_poz.z+15), glm::vec3(0, 1, 0));
					else
						FP_player2.set(glm::vec3(p2_poz.x, 10, p2_poz.z), glm::vec3(p2_poz.x, 0, p2_poz.z + 15), glm::vec3(0, 1, 0));

					rotate = glm::rotate(model_matrix, alpha, glm::vec3(0, 1, 0));
				}
				else if (p2_poz.x <= 0 && 0 >= p2_poz.z && p2_poz.z > -track_length){

					p2_poz.z -= speed2;
					stop2++;
					alpha = 180;
					rotate = glm::rotate(model_matrix, alpha, glm::vec3(0, 1, 0));
					if (id_player2 == 2)
						FP_player2.set(glm::vec3(p2_poz.x, 10, p2_poz.z-4), glm::vec3(p2_poz.x, 10, p2_poz.z - 15), glm::vec3(0, 1, 0));
					else
						FP_player2.set(glm::vec3(p2_poz.x, 10, p2_poz.z), glm::vec3(p2_poz.x, 10, p2_poz.z - 15), glm::vec3(0, 1, 0));
				}
				else if (p2_poz.z <= -track_length){

						//theta -= 0.5;

						//alpha += 0.5;

						theta -= speed2;

						alpha += speed2;

					rotate = glm::rotate(model_matrix, theta-90, glm::vec3(0, 1, 0));
					p2_poz.z = cos(theta/180*PI)*(radius + w *3/ 2) - track_length;
					p2_poz.x = sin(theta/180*PI)*(radius + w*3 / 2);
					
					FP_player2.set(glm::vec3(p2_poz.x, 10, p2_poz.z), glm::vec3(p2_poz.x + cos((alpha + 90) / 180 * PI) * 10, 10, p2_poz.z + sin((alpha + 90) / 180 * PI) * 10), glm::vec3(0, 1, 0));
				}
			

			translate = glm::translate(model_matrix, glm::vec3(p2_poz.x, 0, p2_poz.z));

			scale = glm::scale(model_matrix, glm::vec3(scale_factor2, scale_factor2, scale_factor2));

	
			glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(translate*scale*rotate));

			glBindVertexArray(p2_vao);

			glDrawElements(GL_TRIANGLES, p2_num_indices, GL_UNSIGNED_INT, 0);

			glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(glm::mat4()));	//identity


			//Sfarsit miscare JUCATOR2

			//MISCARE JUCATOR3

			//cand sunt in semicerc ii dau sa creasca/scada la unde ma uit si la x si la z
			

			if (p3_poz.z > 0){


				if (stop3 == 0){

					//if (alpha3 < 180)
						//alpha3 += 0.25;
					alpha3 += speed3;

					p3_poz.z = sin(alpha3/180*PI)*(radius + 5*w / 2);
					p3_poz.x = cos(alpha3/180*PI)*(radius + 5*w / 2);
					FP_player3.set(glm::vec3(p3_poz.x, 10, p3_poz.z), glm::vec3(p3_poz.x + cos((alpha3 + 90) / 180 * PI) * 10, 10, p3_poz.z + sin((alpha3 + 90) / 180 * PI) * 10), glm::vec3(0, 1, 0));

					rotate = glm::rotate(model_matrix, -alpha3, glm::vec3(0, 1, 0));
				}
				else{

					p3_poz.x = radius + 5*w / 2;
					p3_poz.z = 0;

					alpha3 = 0;
					FP_player3.set(glm::vec3(p3_poz.x, 10, p3_poz.z), glm::vec3(p3_poz.x, 10, p3_poz.z - 10), glm::vec3(0, 1, 0));
	
				}
			}
			else if (p3_poz.x>0 && 0 >= p3_poz.z && p3_poz.z > -track_length){

				p3_poz.z += speed3;
				alpha3 = 0;

				if (id_player3 == 2)
					FP_player3.set(glm::vec3(p3_poz.x, 10, p3_poz.z+4), glm::vec3(p3_poz.x, 0, p3_poz.z + 15), glm::vec3(0, 1, 0));
				else
					FP_player3.set(glm::vec3(p3_poz.x, 10, p3_poz.z), glm::vec3(p3_poz.x, 0, p3_poz.z + 15), glm::vec3(0, 1, 0));

				rotate = glm::rotate(model_matrix, alpha3, glm::vec3(0, 1, 0));
			}
			else if (p3_poz.x <= 0 && 0 >= p3_poz.z && p3_poz.z > -track_length ){

				p3_poz.z -= speed3;
				stop3++;
				alpha3 = 180;
				rotate = glm::rotate(model_matrix, alpha3, glm::vec3(0, 1, 0));

				if (id_player3 ==2)
					FP_player3.set(glm::vec3(p3_poz.x, 10, p3_poz.z-4), glm::vec3(p3_poz.x, 10, p3_poz.z - 10), glm::vec3(0, 1, 0));
				else
					FP_player3.set(glm::vec3(p3_poz.x, 10, p3_poz.z), glm::vec3(p3_poz.x, 10, p3_poz.z - 10), glm::vec3(0, 1, 0));

			}
			else if (p3_poz.z <= -track_length){
				//if (theta3 <= 360)
					theta3 -= speed3;
					alpha3 += speed3;

				rotate = glm::rotate(model_matrix, theta3-90, glm::vec3(0, 1, 0));
				p3_poz.z = cos(theta3/180*PI)*(radius + 5*w / 2) - track_length;
				p3_poz.x = sin(theta3/180*PI)*(radius + 5*w / 2);
				
				FP_player3.set(glm::vec3(p3_poz.x, 10, p3_poz.z), glm::vec3(p3_poz.x + cos((alpha3 + 90) / 180 * PI) * 10, 10, p3_poz.z + sin((alpha3 + 90) / 180 * PI) * 10), glm::vec3(0, 1, 0));
			}



			//SFARSIT MISCARE JUCATOR3

			//rotate = glm::rotate(model_matrix, angle, glm::vec3(0, 1, 0));
			translate = glm::translate(model_matrix, glm::vec3(p3_poz.x, 0, p3_poz.z));
			//glm::mat4 scale = glm::scale(model_matrix, glm::vec3(4.0, 4.0, 4.0));
			scale = glm::scale(model_matrix, glm::vec3(scale_factor3, scale_factor3, scale_factor3));

			glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(translate*scale*rotate));

			glBindVertexArray(p3_vao);
			//glDrawElements(GL_TRIANGLES, mesh_num_indices, GL_UNSIGNED_INT, 0);
			glDrawElements(GL_TRIANGLES, p3_num_indices, GL_UNSIGNED_INT, 0);

			glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(glm::mat4()));	//identity


			

			//glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(glm::mat4()));	//identity

			switch (cadran)
			{
			case 1:

				translate = glm::translate(model_matrix, glm::vec3(cam_poz.x, cam_poz.y+5, cam_poz.z));
				rotate = glm::rotate(model_matrix, (angle * 180) / (float)PI-90, glm::vec3(0, 1, 0));
				break;

			case 2:

				translate = glm::translate(model_matrix, glm::vec3(cam_poz.x, cam_poz.y+5, cam_poz.z));
				rotate = glm::rotate(model_matrix, (angle * 180) / (float)PI+90, glm::vec3(0, 1, 0));
				break;

			case 3:

				translate = glm::translate(model_matrix, glm::vec3(p1_poz.x + w * 5 / 2, cam_poz.y+5, cam_poz.z));
				rotate = glm::rotate(model_matrix, (float)-90, glm::vec3(0, 1, 0));
				break;

			case 4:

				translate = glm::translate(model_matrix, glm::vec3(p1_poz.x - w * 5 / 2 , cam_poz.y+5, cam_poz.z));
				rotate = glm::rotate(model_matrix, (float)+90, glm::vec3(0, 1, 0));
				break;

			default:
				break;
			}

			scale = glm::scale(model_matrix, glm::vec3(10, 10, 10));

			glUniformMatrix4fv(glGetUniformLocation(gl_program_shader, "model_matrix"), 1, false, glm::value_ptr(translate*scale*rotate));

			glBindVertexArray(camera_vao);

			glDrawElements(GL_TRIANGLES, camera_num_indices, GL_UNSIGNED_INT, 0);


			if (isOut()){
				//cout << "SOMN " << j++ << endl;
				speed1 = original_speed/5;
			}
			else{
				speed1 = original_speed;
			}
			


		}




	}
	//functie chemata dupa ce am terminat cadrul de desenare (poate fi folosita pt modelare/simulare)
	void notifyEndFrame(){}
	//functei care e chemata cand se schimba dimensiunea ferestrei initiale
	void notifyReshape(int width, int height, int previos_width, int previous_height){
		//reshape
		if(height==0) height=1;
		screen_width = width;
		screen_height = height;
		float aspect = width*0.5f / height;
		projection_matrix = glm::perspective(75.0f, aspect,0.1f, 10000.0f);
	}


	//--------------------------------------------------------------------------------------------
	//functii de input output --------------------------------------------------------------------
	
	//tasta apasata
	void notifyKeyPressed(unsigned char key_pressed, int mouse_x, int mouse_y){
		if(key_pressed == 27) lab::glut::close();	//ESC inchide glut si 
		if(key_pressed == 32) {
			//SPACE reincarca shaderul si recalculeaza locatiile (offseti/pointeri)
			glDeleteProgram(gl_program_shader);
			gl_program_shader = lab::loadShader("shadere\\shader_vertex.glsl", "shadere\\shader_fragment.glsl");
		}
		if(key_pressed == 'i'){
			static bool wire =true;
			wire=!wire;
			glPolygonMode(GL_FRONT_AND_BACK, (wire?GL_LINE:GL_FILL));
		}
		if (key_pressed == 'w') { TV_camera.translateForward(3.0f); }
		if (key_pressed == 'a') { TV_camera.translateRight(-3.0f); }
		if (key_pressed == 's') { TV_camera.translateForward(-3.0f); }
		if (key_pressed == 'd') { TV_camera.translateRight(3.0f); }
		if (key_pressed == 'r') { TV_camera.translateUpword(3.0f); }
		if (key_pressed == 'f') { TV_camera.translateUpword(-3.0f); }
		if (key_pressed == 'q') { TV_camera.rotateFPSoY(PI / 45); }
		if (key_pressed == 'e') { TV_camera.rotateFPSoY(-PI / 45); }
		if (key_pressed == 'z') { TV_camera.rotateFPSoZ(-PI / 45); }
		if (key_pressed == 'c') { TV_camera.rotateFPSoZ(PI / 45); }
		if (key_pressed == 't') { TV_camera.rotateFPSoX(PI / 45); }
		if (key_pressed == 'g') { TV_camera.rotateFPSoX(-PI / 45); }
		//if (key_pressed == 'o') { TV_camera.set(glm::vec3(0, 0, 40), glm::vec3(0, 0, 0), glm::vec3(0, 1, 0)); }
		if (key_pressed == '1') { TP_player1.rotateTPSoX(PI / 15, 40.0f); }
		if (key_pressed == '2') { TP_player1.rotateTPSoX(-PI / 15, 40.0f); }
		if (key_pressed == '3') { TP_player1.rotateTPSoY(PI / 15, 40.0f); }
		if (key_pressed == '4') { TP_player1.rotateTPSoY(-PI / 15, 40.0f); }
		if (key_pressed == '5') { TP_player1.rotateTPSoZ(PI / 15, 40.0f); }
		if (key_pressed == '6') { TP_player1.rotateTPSoZ(-PI / 15, 40.0f); }

		if (key_pressed == '0'){

			TV_camera.set(glm::vec3(0, 200, -track_length / 2), glm::vec3(0, 0, 0), glm::vec3(0, 1, 0));
			tv = true;
			FP1 = false;
			FP2 = false;
			FP3 = false;
			TP1 = false;

		}

		if (key_pressed == '7'){

			tv = false;
			FP1 = true;
			FP2 = false;
			FP3 = false;
			TP1 = false;

		}

		if (key_pressed == '8'){

			tv = false;
			FP1 = false;
			FP2 = true;
			FP3 = false;
			TP1 = false;

		}

		if (key_pressed == '9'){

			tv = false;
			FP1 = false;
			FP2 = false;
			FP3 = true;
			TP1 = false;

		}

		if (key_pressed == 'x'){

			tv = false;
			FP1 = false;
			FP2 = false;
			FP3 = false;
			TP1 = true;

		}

	}
	//tasta ridicata
	void notifyKeyReleased(unsigned char key_released, int mouse_x, int mouse_y){

		


	}

	//tasta speciala (up/down/F1/F2..) apasata
	void notifySpecialKeyPressed(int key_pressed, int mouse_x, int mouse_y){
		if(key_pressed == GLUT_KEY_F1) lab::glut::enterFullscreen();
		if(key_pressed == GLUT_KEY_F2) lab::glut::exitFullscreen();
		
		if (key_pressed == GLUT_KEY_UP) { 

			p1_up = true;
		}
		if (key_pressed == GLUT_KEY_DOWN) {
			
			p1_down = true;

		}
		if (key_pressed == GLUT_KEY_RIGHT) {
 
			p1_right = true;
		}

		if (key_pressed == GLUT_KEY_LEFT) { 
			p1_left = true;

		}
	}
	//tasta speciala ridicata
	void notifySpecialKeyReleased(int key_released, int mouse_x, int mouse_y){
	
		if (key_released == GLUT_KEY_UP) {
			p1_up = false;
		}
		
		if (key_released == GLUT_KEY_DOWN) {
			p1_down = false;
		}

		if (key_released == GLUT_KEY_RIGHT) {
			p1_right = false;
		}
		
		if (key_released == GLUT_KEY_LEFT) {
			p1_left = false;
		}

	}
	//drag cu mouse-ul
	void notifyMouseDrag(int mouse_x, int mouse_y){ }
	//am miscat mouseul (fara sa apas vreun buton)
	void notifyMouseMove(int mouse_x, int mouse_y){ }
	//am apasat pe un boton
	void notifyMouseClick(int button, int state, int mouse_x, int mouse_y){ }
	//scroll cu mouse-ul
	void notifyMouseScroll(int wheel, int direction, int mouse_x, int mouse_y){ std::cout<<"Mouse scroll"<<std::endl;}



};

	int main(){

		//cout << cos(0.785375)<< endl;

	//initializeaza GLUT (fereastra + input + context OpenGL)
	lab::glut::WindowInfo window(std::string("lab shadere 3 - camera"),800,600,100,100,true);
	lab::glut::ContextInfo context(3,1,false);
	lab::glut::FramebufferInfo framebuffer(true,true,true,true);
	lab::glut::init(window,context, framebuffer);

	//initializeaza GLEW (ne incarca functiile openGL, altfel ar trebui sa facem asta manual!)
	glewExperimental = true;
	glewInit();
	std::cout<<"GLEW:initializare"<<std::endl;

	Laborator mylab;
	lab::glut::setListener(&mylab);

	//taste
	std::cout<<"Taste:"<<std::endl<<"\tESC ... iesire"<<std::endl<<"\tSPACE ... reincarca shadere"<<std::endl<<"\ti ... toggle wireframe"<<std::endl<<"\to ... reseteaza camera"<<std::endl;
	std::cout<<"\tw ... translatie camera in fata"<<std::endl<<"\ts ... translatie camera inapoi"<<std::endl;
	std::cout<<"\ta ... translatie camera in stanga"<<std::endl<<"\td ... translatie camera in dreapta"<<std::endl;
	std::cout<<"\tr ... translatie camera in sus"<<std::endl<<"\tf ... translatie camera jos"<<std::endl;
	std::cout<<"\tq ... rotatie camera FPS pe Oy, minus"<<std::endl<<"\te ... rotatie camera FPS pe Oy, plus"<<std::endl;
	std::cout<<"\tz ... rotatie camera FPS pe Oz, minus"<<std::endl<<"\tc ... rotatie camera FPS pe Oz, plus"<<std::endl;

	//std::cout << Laborator::getSector(-2,-3)<<endl;



	//run
	lab::glut::run();
	  
	return 0;
}