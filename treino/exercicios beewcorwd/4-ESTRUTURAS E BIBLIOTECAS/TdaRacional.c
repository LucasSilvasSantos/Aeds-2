#include <stdio.h>

int gcd(int a, int b) {
    a = a < 0 ? -a : a;
    b = b < 0 ? -b : b;
    if (a == 0 && b == 0) return 1;
    while (b != 0) {
        int t = a % b;
        a = b;
        b = t;
    }
    return a;
}

int main() {
    int entrada, i, n1, d1, n2, d2, num, den, origNum, origDen, g;
    char slash[4], op[4];

    if (scanf("%d", &entrada) != 1) return 0;

    for (i = 0; i < entrada; i++) {
        /* formato: n1 / d1 op n2 / d2   (tokens separados por espaço) */
        if (scanf("%d %s %d %s %d %s %d",
                  &n1, slash, &d1, op, &n2, slash, &d2) != 7) break;

        num = 0; den = 1;

        if      (op[0] == '+') { num = n1 * d2 + n2 * d1; den = d1 * d2; }
        else if (op[0] == '-') { num = n1 * d2 - n2 * d1; den = d1 * d2; }
        else if (op[0] == '*') { num = n1 * n2;            den = d1 * d2; }
        else                   { num = n1 * d2;            den = d1 * n2; }

        if (den == 0) { printf("Denominador zero\n"); continue; }

        origNum = num;
        origDen = den;

        g   = gcd(num, den);
        num /= g;
        den /= g;

        if (den < 0) { den = -den; num = -num; }

        printf("%d/%d = %d/%d\n", origNum, origDen, num, den);
    }

    return 0;
}
