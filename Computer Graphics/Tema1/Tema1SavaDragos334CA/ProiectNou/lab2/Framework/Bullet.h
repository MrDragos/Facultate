#include "Circle2D.h"
#include "Point2D.h"
#include "Color.h"

class Bullet{

	

	public:
	
		const int size = 2;
		Circle2D *bullet;
		bool collision = false;

	Bullet(Point2D p,Color c){
		bullet = new Circle2D(p,size,c,true);
	}

	void detectColision(){

	}

	~Bullet(){

	}

};