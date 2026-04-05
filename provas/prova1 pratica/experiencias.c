#include <stdio.h>


int main(){
 int N;
 float percentualC, percentualR, percentualS;
 int nC=0,nS=0,nR=0;
int total=0;
scanf("%d",&N);
 for( int i =0 ; i <N; i++)
 {
    int numerocobaias;  
    char cobaias;
 scanf("%d %c",&numerocobaias,&cobaias);
    total+=numerocobaias;
 if(cobaias == 'C'){
  nC+= numerocobaias;
 }
if(cobaias == 'S'){
  nS+= numerocobaias;
 }
if(cobaias == 'R'){
  nR+= numerocobaias;
 }
 
}
printf("total %d\n",total);
 printf("total de coelhos %d\n",nC);
 printf("total de ratos %d\n",nR);    
 printf("total de sapos %d\n",nS);
printf("percentual coelhos %2.f\n",percentualC=nC*100/total);
printf("percentual Ratos %2.f\n",percentualR=nR*100/total);
printf("percentual Sapos %2.f\n",percentualS=nS*100/total);



}