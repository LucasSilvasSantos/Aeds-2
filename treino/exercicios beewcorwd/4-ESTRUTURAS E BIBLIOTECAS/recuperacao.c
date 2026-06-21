#include <stdio.h>

#define MAX_LEN 10001

char pre_buf[MAX_LEN];
char in_buf[MAX_LEN];
char out_buf[MAX_LEN];
int  outLen;

void posFixo(char* pre, char* in_, int pIni, int pFim, int iIni, int iFim) {
    if (pIni > pFim) return;

    char raiz = pre[pIni];
    int pos = iIni;
    while (in_[pos] != raiz) pos++;

    int tamEsq = pos - iIni;

    posFixo(pre, in_, pIni + 1,         pIni + tamEsq, iIni,    pos - 1);
    posFixo(pre, in_, pIni + tamEsq + 1, pFim,          pos + 1, iFim);

    out_buf[outLen++] = raiz;
}

int strLen(char* s) {
    int i = 0;
    while (s[i]) i++;
    return i;
}

int main() {
    while (scanf("%s %s", pre_buf, in_buf) == 2) {
        outLen = 0;
        int n = strLen(pre_buf);
        posFixo(pre_buf, in_buf, 0, n - 1, 0, n - 1);
        out_buf[outLen] = '\0';
        printf("%s\n", out_buf);
    }
    return 0;
}
