//-------------------------------------------------------------------------------------------------
// Descriere: clasa MyTrack
//
// Autor: Sava Dragos
// Grupa: 334CA
//-------------------------------------------------------------------------------------------------


#pragma once
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

#include "VertexFormat.h"

#define PI 3.14159265359

class MyTrack{
	

private:
	glm::mat4 original_model_matrix;
	glm::mat4 model_matrix, view_matrix, projection_matrix;								//matrici 4x4 pt modelare vizualizare proiectie
	unsigned int location_model_matrix, location_view_matrix, location_projection_matrix;//locatii la nivel de shader ale matricilor (un pointer/offset)
	unsigned int gl_program_shader;														//id-ul de opengl al obiectului de tip program shader

public:

	//definim containere pentru date
	std::vector<VertexFormat> vertexi;
	std::vector<glm::uvec3> indexi;

	int size;	//lungimea pistei
	int radius;
	unsigned int track_vbo, track_ibo, track_vao, track_num_indexi;

	MyTrack(){

		
	}


	MyTrack(int size, int radius, glm::vec3 culoare){

		this->size = size;
		this->radius = radius;

		int x = 0; 
		int y = 0;
		int z = 0;


		VertexFormat vf[3000];

		for (int i = 0; i < 180; i++) {
			vf[i].culoare = culoare;
		}


		int k = 0;

		for (float angle = 0; angle <= PI; angle += PI / 180) {
			
			vf[k].pozitie = glm::vec3(x + cos(angle) * radius, y, z + sin(angle) * radius); 
			vf[k].culoare = culoare;
			vertexi.push_back(vf[k]);
			k++;
			
			vf[k].pozitie = glm::vec3(x + cos(angle) * radius, y, z + sin(angle) * radius); 
			vf[k].culoare = glm::vec3(0, 0.4, 0);
			vertexi.push_back(vf[k]);
			k++;
			
			vf[k].pozitie = glm::vec3(x + cos(angle) * (radius + 10), y, z + sin(angle) * (radius + 10)); 
			vf[k].culoare = glm::vec3(0, 0.4, 0);
			vertexi.push_back(vf[k]);
			k++;
			
			vf[k].pozitie = glm::vec3(x + cos(angle) * (radius + 10), y, z + sin(angle) * (radius + 10)); 
			vf[k].culoare = glm::vec3(1, 1, 1);
			vertexi.push_back(vf[k]);
			k++;
			
			vf[k].pozitie = glm::vec3(x + cos(angle) * (radius + 20), y, z + sin(angle) * (radius + 20)); 
			vf[k].culoare = glm::vec3(1, 1, 1);
			vertexi.push_back(vf[k]);
			k++;
			
			vf[k].pozitie = glm::vec3(x + cos(angle) * (radius + 20), y, z + sin(angle) * (radius + 20)); 
			vf[k].culoare = glm::vec3(0, 0.4, 0);
			vertexi.push_back(vf[k]);
			k++;

			vf[k].pozitie = glm::vec3(x + cos(angle) * (radius + 30), y, z + sin(angle) * (radius + 30)); 
			vf[k].culoare = glm::vec3(0, 0.4, 0);
			vertexi.push_back(vf[k]);

			if (k >= 13) {
			
				indexi.push_back(glm::uvec3(0, k - 13, k - 6));
				indexi.push_back(glm::uvec3(k-12, k - 11, k - 4));
				indexi.push_back(glm::uvec3(k-12, k - 5, k - 4));
				indexi.push_back(glm::uvec3(k - 10, k - 9, k - 2));
				indexi.push_back(glm::uvec3(k - 10, k - 3, k - 2));
				indexi.push_back(glm::uvec3(k - 8, k - 7, k));
				indexi.push_back(glm::uvec3(k - 8, k - 1, k));

			}

			k++;
		}

		float x_original = x;
		float w = 10;	//latimea unei benzi

		//Banda 1

		x = x_original + radius;

		//k-3
		vf[k].pozitie = glm::vec3(x, y, z);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);
		k++;
		
		//k-2
		vf[k].pozitie = glm::vec3(x, y, z-size);
		vf[k].culoare = glm::vec3(0,0.4,0);
		vertexi.push_back(vf[k]);
		k++;
		
		//k-1
		vf[k].pozitie = glm::vec3(x+w, y, z-size);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);
		k++;

		//k
		vf[k].pozitie = glm::vec3(x+w, y, z);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);


		indexi.push_back(glm::uvec3(k - 2, k - 1, k-3));
		indexi.push_back(glm::uvec3(k-3, k - 1, k));

		k++;

		//Banda 2

		x = x_original - radius;
		
		//k-3
		vf[k].pozitie = glm::vec3(x, y, z);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);

		k++;
		
		//k-2
		vf[k].pozitie = glm::vec3(x, y, z-size);
		vf[k].culoare = glm::vec3(0,0.4,0);
		vertexi.push_back(vf[k]);

		k++;
		
		//k-1
		vf[k].pozitie = glm::vec3(x-w, y, z-size);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);

		k++;
		
		//k
		vf[k].pozitie = glm::vec3(x-w, y, z);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);

		indexi.push_back(glm::uvec3(k - 2, k - 1, k - 3));
		indexi.push_back(glm::uvec3(k - 3, k - 1, k));

		k++;

		//Banda 3

		x = x_original - radius;

		//k-3
		vf[k].pozitie = glm::vec3(x-w, y, z);
		vf[k].culoare = glm::vec3(1, 1, 1);
		vertexi.push_back(vf[k]);

		k++;

		//k-2
		vf[k].pozitie = glm::vec3(x-w, y, z - size);
		vf[k].culoare = glm::vec3(1, 1, 1);
		vertexi.push_back(vf[k]);

		k++;

		//k-1
		vf[k].pozitie = glm::vec3(x - 2*w, y, z - size);
		vf[k].culoare = glm::vec3(1, 1, 1);
		vertexi.push_back(vf[k]);

		k++;

		//k
		vf[k].pozitie = glm::vec3(x - 2*w, y, z);
		vf[k].culoare = glm::vec3(1, 1, 1);
		vertexi.push_back(vf[k]);


		indexi.push_back(glm::uvec3(k - 2, k - 1, k - 3));
		indexi.push_back(glm::uvec3(k - 3, k - 1, k));

		k++;

		//Banda 4

		x = x_original + radius;

		//k-3
		vf[k].pozitie = glm::vec3(x+w, y, z);
		vf[k].culoare = glm::vec3(1, 1, 1);
		vertexi.push_back(vf[k]);

		k++;

		//k-2
		vf[k].pozitie = glm::vec3(x+w, y, z - size);
		vf[k].culoare = glm::vec3(1, 1, 1);
		vertexi.push_back(vf[k]);

		k++;

		//k-1
		vf[k].pozitie = glm::vec3(x + 2 * w, y, z - size);
		vf[k].culoare = glm::vec3(1, 1, 1);
		vertexi.push_back(vf[k]);

		k++;

		//k
		vf[k].pozitie = glm::vec3(x + 2 * w, y, z);
		vf[k].culoare = glm::vec3(1, 1, 1);
		vertexi.push_back(vf[k]);


		indexi.push_back(glm::uvec3(k - 2, k - 1, k - 3));
		indexi.push_back(glm::uvec3(k - 3, k - 1, k));

		k++;

		//Banda 5

		x = x_original + radius;

		//k-3
		vf[k].pozitie = glm::vec3(x+2*w, y, z);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);

		k++;

		//k-2
		vf[k].pozitie = glm::vec3(x+2*w, y, z - size);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);

		k++;

		//k-1
		vf[k].pozitie = glm::vec3(x + 3*w, y, z - size);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);

		k++;

		//k
		vf[k].pozitie = glm::vec3(x + 3*w, y, z);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);


		indexi.push_back(glm::uvec3(k - 2, k - 1, k - 3));
		indexi.push_back(glm::uvec3(k - 3, k - 1, k));

		k++;


		//Banda 6

		x = x_original - radius;

		//k-3
		vf[k].pozitie = glm::vec3(x-2*w, y, z);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);

		k++;

		//k-2
		vf[k].pozitie = glm::vec3(x-2*w, y, z - size);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);

		k++;

		//k-1
		vf[k].pozitie = glm::vec3(x - 3 * w, y, z - size);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);

		k++;

		//k
		vf[k].pozitie = glm::vec3(x - 3 * w, y, z);
		vf[k].culoare = glm::vec3(0, 0.4, 0);
		vertexi.push_back(vf[k]);


		indexi.push_back(glm::uvec3(k - 2, k - 1, k - 3));
		indexi.push_back(glm::uvec3(k - 3, k - 1, k));

		k++;


		////Al doilea semicerc
		x = x_original;

		vf[k].pozitie = glm::vec3(x, y, z - size);
		vf[k].culoare = culoare;
		vertexi.push_back(vf[k]);
		int old_k = k;
		k++;
		

		for (float angle = 0; angle <= PI; angle += PI / 180) {

			vf[k].pozitie = glm::vec3(x - cos(angle) * radius, y, z - sin(angle) * radius - size); 
			vf[k].culoare = culoare;
			vertexi.push_back(vf[k]);
			k++;

			vf[k].pozitie = glm::vec3(x - cos(angle) * (radius), y, z - sin(angle) * (radius)-size); 
			vf[k].culoare = glm::vec3(0, 0.4, 0);
			vertexi.push_back(vf[k]);
			k++;

			vf[k].pozitie = glm::vec3(x - cos(angle) * (radius + 10), y, z - sin(angle) * (radius + 10) - size); 
			vf[k].culoare = glm::vec3(0, 0.4, 0);
			vertexi.push_back(vf[k]);
			k++;

			vf[k].pozitie = glm::vec3(x - cos(angle) * (radius + 10), y, z - sin(angle) * (radius + 10) - size); 
			vf[k].culoare = glm::vec3(1, 1, 1);
			vertexi.push_back(vf[k]);
			k++;

			vf[k].pozitie = glm::vec3(x - cos(angle) * (radius + 20), y, z - sin(angle) * (radius + 20) - size); 
			vf[k].culoare = glm::vec3(1, 1, 1);
			vertexi.push_back(vf[k]);
			k++;
			
			vf[k].pozitie = glm::vec3(x - cos(angle) * (radius + 20), y, z - sin(angle) * (radius + 20) - size); 
			vf[k].culoare = glm::vec3(0, 0.4, 0);
			vertexi.push_back(vf[k]);
			k++;

			vf[k].pozitie = glm::vec3(x - cos(angle) * (radius + 30), y, z - sin(angle) * (radius + 30) - size); 
			vf[k].culoare = glm::vec3(0, 0.4, 0);
			vertexi.push_back(vf[k]);

			if (k >= old_k + 13) {
				
				indexi.push_back(glm::uvec3(k - 12, k - 11, k - 4));
				indexi.push_back(glm::uvec3(k - 12, k - 5, k - 4));

				indexi.push_back(glm::uvec3(k - 10, k - 9, k - 2));
				indexi.push_back(glm::uvec3(k - 10, k - 3, k - 2));

				indexi.push_back(glm::uvec3(k - 8, k - 7, k)); 
				indexi.push_back(glm::uvec3(k - 8, k - 1, k));

			}

			k++;
		}


		//creaza vao
		glGenVertexArrays(1, &track_vao);
		glBindVertexArray(track_vao);

		//creeaza vbo
		glGenBuffers(1, &track_vbo);
		glBindBuffer(GL_ARRAY_BUFFER, track_vbo);
		glBufferData(GL_ARRAY_BUFFER, sizeof(VertexFormat)*vertexi.size(), &vertexi[0], GL_STATIC_DRAW);

		//creeaza ibo
		glGenBuffers(1, &track_ibo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, track_ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(unsigned int)*indexi.size() * 3, &indexi[0], GL_STATIC_DRAW);

		// metoda 1: seteaza atribute folosind pipe-urile interne ce fac legatura OpenGL - GLSL, in shader folosim layout(location = pipe_index)
		// metoda cea mai buna, specificare explicita prin qualificator layout)
		glEnableVertexAttribArray(0);																			//activare pipe 0
		glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, sizeof(VertexFormat), (void*)0);							//trimite pozitii pe pipe 0
		glEnableVertexAttribArray(1);																			//activare pipe 1
		glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, sizeof(VertexFormat), (void*)(sizeof(glm::vec3)));		//trimite normale pe pipe 1

		track_num_indexi = indexi.size() * 3;


	}

	void BindVertexArray()
	{
		glBindVertexArray(track_vao);
		glDrawElements(GL_TRIANGLES, track_num_indexi, GL_UNSIGNED_INT, 0);
	}

	~MyTrack(){
		glDeleteBuffers(1, &track_vbo);
		glDeleteBuffers(1, &track_ibo);
		glDeleteVertexArrays(1, &track_vao);
	}

};