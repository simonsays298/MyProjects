#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define INT_MAX 32767

void TABELA (int N, int M, int x, int y,char c,int p, int X1,int X2, int X3, int Y1, int Y2, int Y3 )//creeaza tabela de N linii si M coloane cu x fiind coordonata liniei si y coordonata coloanei
{
		int i,j,k;
	
	
	for(i=0; i<=N+1; ++i){

		if(i==0 || i==N+1)//ccreeaza marginea superioara si inferioara a tabelei

			for(j=0; j<=M+1; ++j)

				printf("%c", '*');
		
		else 

			for(k=0; k<=M+1; ++k){

				if(k==0 || k==M+1)//creeaza marginile din dreapat si stanga ale tabelei

					printf("%c",'*');

				else
					if(y==k && x==i)// in caz contrar afiseaza personajul

						printf("%c",c);

					else 
						if(p>=1 && i==X1 && k==Y1)//atunci cand p este diferit de 0 va afisa pe tabela pe rand cele 1, 2 sau 3 obstacole

							printf("x");

						else

							if(p>=2 && i==X2 && k==Y2)

								printf("x");

							else 
								if(p<=3 && i==X3 && k==Y3)

									printf("x");

								else// daca coorodnatele nu indeplinesc nici una din cerintele dorite va afisa doar spatiu

									printf(" ");
			}
		
			
		printf("\n");
	}


}
		int distmin2(int x,int y, int X2, int Y2){// calculeaza distanta minima intre personaj si obstacol

			int MINIM=INT_MAX;

			int distmin;

				if(fabs(x-(X2-1))+fabs(y-(Y2-1))<MINIM){

					MINIM=fabs(x-(X2-1))+fabs(y-(Y2-1));

					 distmin=1;
				}

				if(fabs(x-(X2-1))+fabs(y-(Y2+1))<MINIM){

					MINIM=fabs(x-(X2-1))+fabs(y-(Y2+1));

					
					 distmin=2;

				}
				if(fabs(x-(X2+1))+fabs(y-(Y2+1))<MINIM){

					MINIM=fabs(x-(X2+1))+fabs(y-(Y2+1));

					
				 distmin=3;
				}
				if(fabs(x-(X2+1))+fabs(y-(Y2-1))<MINIM){

					MINIM=fabs(x-(X2+1))+fabs(y-(Y2-1));

					 distmin=4;
				}
				
				if(fabs(x-X2)+fabs(y-(Y2-1))<MINIM){

					MINIM=fabs(x-X2)+fabs(y-(Y2-1));

					
					 distmin=5;
				}

				if(fabs(x-X2)+fabs(y-(Y2+1))<MINIM){

					MINIM=fabs(x-X2)+fabs(y-(Y2+1));

					
					distmin=6;

				}

				if(fabs(x-(X2-1))+fabs(y-Y2)<MINIM){

					MINIM=fabs(x-(X2-1))+fabs(y-Y2);

					
					 distmin=7;
				}

				if(fabs(x-(X2+1))+fabs(y-Y2)<MINIM){

					MINIM=fabs(x-(X2+1))+fabs(y-Y2);

					
					distmin=8;
				}
				return distmin;
			}
		int distmin3(int x,int y, int X3, int Y3){// calculeaza distanta minima intre personaj si obstacol

		int MINIM=INT_MAX;

		int distmin;

				if(fabs(x-(X3-1))+fabs(y-(Y3-1))<MINIM){

					MINIM=fabs(x-(X3-1))+fabs(y-(Y3-1));

					 distmin=1;
				}

				if(fabs(x-(X3-1))+fabs(y-(Y3+1))<MINIM){

					MINIM=fabs(x-(X3-1))+fabs(y-(Y3+1));

					
					 distmin=2;

				}
				if(fabs(x-(X3+1))+fabs(y-(Y3+1))<MINIM){

					MINIM=fabs(x-(X3+1))+fabs(y-(Y3+1));

					
				 distmin=3;
				}
				if(fabs(x-(X3+1))+fabs(y-(Y3-1))<MINIM){

					MINIM=fabs(x-(X3+1))+fabs(y-(Y3-1));

					 distmin=4;
				}
				
				if(fabs(x-X3)+fabs(y-(Y3-1))<MINIM){

					MINIM=fabs(x-X3)+fabs(y-(Y3-1));

					
					 distmin=5;
				}

				if(fabs(x-X3)+fabs(y-(Y3+1))<MINIM){

					MINIM=fabs(x-X3)+fabs(y-(Y3+1));

					
					distmin=6;

				}

				if(fabs(x-(X3-1))+fabs(y-Y3)<MINIM){

					MINIM=fabs(x-(X3-1))+fabs(y-Y3);

					
					 distmin=7;
				}

				if(fabs(x-(X3+1))+fabs(y-Y3)<MINIM){

					MINIM=fabs(x-(X3+1))+fabs(y-Y3);

					
					distmin=8;
				}
				return distmin;
			}

	int distmin1(int x,int y, int X1, int Y1){// calculeaza distanta minima intre personaj si obstacol

		int MINIM=INT_MAX;

		int distmin;

				if(fabs(x-(X1-1))+fabs(y-(Y1-1))<MINIM){

					MINIM=fabs(x-(X1-1))+fabs(y-(Y1-1));

					 distmin=1;
				}

				if(fabs(x-(X1-1))+fabs(y-(Y1+1))<MINIM){

					MINIM=fabs(x-(X1-1))+fabs(y-(Y1+1));

					
					 distmin=2;

				}
				if(fabs(x-(X1+1))+fabs(y-(Y1+1))<MINIM){

					MINIM=fabs(x-(X1+1))+fabs(y-(Y1+1));

					
				 distmin=3;
				}
				if(fabs(x-(X1+1))+fabs(y-(Y1-1))<MINIM){

					MINIM=fabs(x-(X1+1))+fabs(y-(Y1-1));

					 distmin=4;
				}
				
				if(fabs(x-X1)+fabs(y-(Y1-1))<MINIM){

					MINIM=fabs(x-X1)+fabs(y-(Y1-1));

					
					 distmin=5;
				}

				if(fabs(x-X1)+fabs(y-(Y1+1))<MINIM){

					MINIM=fabs(x-X1)+fabs(y-(Y1+1));

					
					distmin=6;

				}

				if(fabs(x-(X1-1))+fabs(y-Y1)<MINIM){

					MINIM=fabs(x-(X1-1))+fabs(y-Y1);

					
					 distmin=7;
				}

				if(fabs(x-(X1+1))+fabs(y-Y1)<MINIM){

					MINIM=fabs(x-(X1+1))+fabs(y-Y1);

					
					distmin=8;
				}
				return distmin;

			}
	
	void change2(int x, int y,int *X2,int *Y2){//modificam coorodonatele obstacolului2

		int d=distmin2(x,y,*X2,*Y2);

		if(d==1){
			*X2=*X2-1;

			*Y2=*Y2-1;
		}

		if(d==2){
			*X2=*X2-1;
			*Y2=*Y2+1;

		}

		if(d==3){
			*X2=*X2+1;

			*Y2=*Y2+1;

		}

		if(d==4){

			*X2=*X2+1;

			*Y2=*Y2-1;
		}

		if(d==5){
			*Y2=*Y2-1;

		
		}

		if(d==6){
			*Y2=*Y2+1;

			

		}
		if(d==7){
			*X2=*X2-1;
			
		}
		if(d==8){
			*X2=*X2+1;
		
		}
	}
		void change3(int x, int y,int *X3,int *Y3){//modificam coorodonatele obstacolului3
		
		int d=distmin3(x,y,*X3,*Y3);
		
		if(d==1){
			*X3=*X3-1;

			*Y3=*Y3-1;
		}
		if(d==2){
			*X3=*X3-1;
			*Y3=*Y3+1;

		}
		if(d==3){
			*X3=*X3+1;

			*Y3=*Y3+1;


		}
		if(d==4){

			*X3=*X3+1;

			*Y3=*Y3-1;
		}

		if(d==5){
			*Y3=*Y3-1;
		
		}

		if(d==6){
			*Y3=*Y3+1;

		}
		if(d==7){
			*X3=*X3-1;

			
		}
		if(d==8){
			*X3=*X3+1;

		}
	}

	void change1(int x, int y,int *X1,int *Y1){//modificam coorodonatele obstacolului1
		
		int d=distmin3(x,y,*X1,*Y1);
		
		if(d==1){

			*X1=*X1-1;

			*Y1=*Y1-1;
		}
		if(d==2){

			*X1=*X1-1;

			*Y1=*Y1+1;

		}
		if(d==3){

			*X1=*X1+1;

			*Y1=*Y1+1;


		}
		if(d==4){

			*X1=*X1+1;

			*Y1=*Y1-1;
		}
		if(d==5){

			*Y1=*Y1-1;
		}

		if(d==6){
			*Y1=*Y1+1;

		}
		if(d==7){

			*X1=*X1-1;

		}
		if(d==8){

			*X1=*X1+1;
		
		}
	}
int main(){

	int M,N,x,y,p,X1,X2,X3,Y1,Y2,Y3,mutare=0,i;

	char c='v';

	char m;

	char mod1,mod2,mod3;

	scanf("%d %d %d %d %d", &N,&M,&y,&x,&p); 

	for(i=1;i<=p;i++){
		if(i==1)

			scanf("%d %d %c",&X1,&Y1,&mod1);//citim coordonatele obtstacolului si tipul acestuia

		if(i==2)

			scanf("%d %d %c",&X2,&Y2,&mod2);

		if(i==3)

			scanf("%d %d %c",&X3,&Y3,&mod3);


	}

	while(1){

		TABELA(N,M,x,y,c,p,X1,X2,X3,Y1,Y2,Y3);

		scanf(" %c", &m);

		mutare++;//contorizam numarul de mutari ale personajului

		if(m=='w'&& x>1){

			c='^';

			x--;
		}

		if(m=='s'&& x<N){

			c='v';

			x++;
		}	

		if(m=='a'&& y>1){

			c='<';

			y--;
		} 	
	
		if(m=='d'&& y<M){

			c='>';

			y++;
		}

		if(m=='q'){

			break;
		}

		if(mutare%2==0){// muatrea obstacolelelor daca mutarea e para numai

		if(p>=1){

			if(mod1=='m'){

				change1(x,y,&X1,&Y1);
			
		}
	}
		if(p>=2){
			if(mod2=='m'){
				change2(x,y,&X2,&Y2);
			}
		}
		if(p>=3){
			if(mod3=='m'){
				change3(x,y,&X3,&Y3);
			
			}
		}
	}
		
		if(p>=1 && x==X1 && y==Y1){//daca un obstacol se suprapune cu personajul
			
			printf("GAME OVER");
			
			printf("\n");
			
			break;
		}
		
		if(p>=2 && x==X2 && y==Y2){//daca un obstacol se suprapune cu personajul
				
		
			printf("GAME OVER");
		
			printf("\n");
		
			break;

		}
		
		if(p>=3 && x==X3 && y==Y3){ //daca un obstacol se suprapune cu personajul
		
			printf("GAME OVER");
		
			printf("\n");
		
			break;
		}
		
		if(x==N && y==M){// daca personajul ajunge in coltul tabelei 
			TABELA(N,M,x,y,c,p,X1,X2,X3,Y1,Y2,Y3);
		
			printf("GAME COMPLETED");
		
			printf("\n");
		
			break;
		}
	
	}

	return 0;
}