// Chamada pública
public static void quickSort(int[] a) {
    if (a == null || a.length < 2) return;
    quickSortRec(a, 0, a.length - 1);
}

// Função recursiva com particionamento de Hoare
private static void quickSortRec(int[] a, int left, int right) {
    if (left >= right) return;
    int i = left;
    int j = right;
    int pivot = a[(left + right) / 2]; // pivô por mediana simples (elemento central)
    while (i <= j) {
        while (a[i] < pivot) i++;
        while (a[j] > pivot) j--;
        if (i <= j) {
            // swap a[i] <-> a[j]
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
            i++;
            j--;
        }
    }
    if (left < j) quickSortRec(a, left, j);
    if (i < right) quickSortRec(a, i, right);
}