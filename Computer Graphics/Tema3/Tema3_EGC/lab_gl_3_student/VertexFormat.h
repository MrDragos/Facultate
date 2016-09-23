//-------------------------------------------------------------------------------------------------
// Autor: Sava Dragos
// Grupa: 334CA
//-------------------------------------------------------------------------------------------------

#pragma once
//incarcator de meshe
#include "lab_mesh_loader.hpp"


using namespace std;

class VertexFormat{

public:

	glm::vec3 pozitie;
	glm::vec3 culoare;

	VertexFormat(){
		pozitie = glm::vec3(0, 0, 0);
		culoare = glm::vec3(1, 1, 1);
	}

	VertexFormat(float px, float py, float pz, float cx, float cy, float cz)
	{
		pozitie = glm::vec3(px, py, pz);
		culoare = glm::vec3(cx, cy, cz);
	}

	// supraincarcarea operatorului de asignare
	VertexFormat operator=(const VertexFormat &vf)
	{
		pozitie = vf.pozitie;
		culoare = vf.culoare;
		return (*this);
	}

	~VertexFormat(){
	
	
	}




};