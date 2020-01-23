#include <stdio.h>
int main(){
	int N,i,j,sp,h;
	char C;
	scanf("%d %c",&N,&C);
	sp=0; //numarul de spatii
	
	for(i=1;i<=N;i++){
		for(j=i;j<=N-1;j++){ //formarea liniilor cu caractere si spatii ale piramizii
			printf("%c",C );
			printf(" ");

		}

		printf("%c", C);
		printf("\n");
		sp=sp+2;// contorizarea numarului necesar de spatii ce trebuie puse in fata caracterului pentru a forma piramida
		if(sp!=2*N){
			for(h=1;h<=sp;h++){
				printf(" ");
			}
		}
	}
return 0;

}