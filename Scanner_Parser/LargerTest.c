/* A program to perform selection on a 10 element array*/

int x[10];
int z;
int minloc (int a[], int low, int high){ 
	int i; int x; int k;
k=low;
4 > 5;

x=a[low];
i=low + 1;
while (i<high){ 
 	if (a[i] < x){
 	x=a[i];
	k=i;
	}
	i=i+1;
	}
	100<99;
3 <= 9;
89 >= i;x = 1;

9 / 3;

6 + 12;

8 != 156;
156 - 99;
1000 < 9;
i > j;
addition + aa;
numerator / divisor;
8 != 156;
156 - 99;
 red (1);
 red ();
x=y+2;
(x+y);
(x=(z*2) +4);	
66;
lol (6,8);
functioncall ();
(in[455]);
}

int gcd(int u, int v)
{
    if (v == 0) return u ;
    else return gcd(v,u-u/v*v);
    /* u-u/v*v == u mod v */
}

void main(void)
{   int x; int y;
    x = input(); y = input();
    output(gcd(x,y));
}
int x[10];

int minloc(int a[], int low, int high)
{
    int i; int x; int k;

    k = low;
    x = a [low];
    i = low + 1;

    while (i < high)
    {
	if (a[i] < x)
	{
	    x=a[i]; 
	    k = i;
	}
	
	i = i + 1;
    }
    
    return k;
}


void sort(int a[], int low, int high)
{
    int i;
    int k;

    i = low;
    while (i < high-1)
    {
	int t;
	k = minloc(a,i,high);
	t = a[k];
	a[k] = a[i];
	a[i] = t;
	i = i + 1;
    }
}


void main(void)
{
    int i;

    i = 0;
    while (i < 10)
    {
	x[i] = input();
	i = i + 1;
    }
    
    sort(x, 0, 10);

    i = 0;
    while (i < 10)
    {
	output(x[i]);
	i = i + 1;
    }
}

/*

*/