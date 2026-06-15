// Nó da Trie: guarda um caractere, flag de fim de palavra e filhos indexados por char ASCII
class NoTrie {
    char elemento;   // caractere armazenado neste nó
    boolean folha;   // true se este nó marca o fim de uma palavra
    NoTrie[] prox;   // array de filhos — índice é o valor ASCII do caractere

    // Construtor da raiz (sem caractere)
    public NoTrie() {
        this('\0');  // raiz usa caractere nulo como placeholder
    }

    // Construtor normal: inicializa o char, marca como não-folha e cria o array de 256 posições (ASCII completo)
    public NoTrie(char c) {
        this.elemento = c;
        this.folha = false;
        this.prox = new NoTrie[256];
    }
}

class ArvoreTrie {
    private final NoTrie raiz;

    // Construtor: cria a raiz sem caractere
    public ArvoreTrie(){
        raiz = new NoTrie();
    }


    // --- PESQUISA ---

    // Método público: inicia a busca pela raiz no índice 0
    public boolean pesquisar(String s) throws Exception {
        return pesquisar(s, raiz, 0);
    }

    // Método recursivo:
    //   se o filho para s[i] não existe → palavra não está na Trie
    //   se chegou no último char (i == comprimento-1) → verifica se o nó é folha (fim de palavra)
    //   senão → desce para o filho correspondente e avança o índice
    //   (o else final nunca é alcançado, mas lança exceção como segurança)
    public boolean pesquisar(String s, NoTrie no, int i) throws Exception {
        boolean resp;
        if(no.prox[s.charAt(i)] == null){
            resp = false;
        } else if(i == s.length() - 1){
            resp = (no.prox[s.charAt(i)].folha == true);
        } else if(i < s.length() - 1){
            resp = pesquisar(s, no.prox[s.charAt(i)], i + 1);
        } else {
            throw new Exception("Erro ao pesquisar!");
        }
        return resp;
    }


    // --- INSERÇÃO ---

    // Método público: inicia a inserção pela raiz no índice 0
    public void inserir(String s) throws Exception {
        inserir(s, raiz, 0);
    }

    // Método recursivo:
    //   se o filho para s[i] não existe → cria o nó filho com o caractere s[i]
    //   se chegou no último char → marca o nó filho como folha (fim de palavra)
    //                               se já era folha, a palavra já existe → exceção
    //   senão → desce para o filho e avança o índice
    //
    // CORREÇÃO: o código original usava if-else aninhado que impedia inserir:
    //   - prefixos de palavras existentes (ex: "car" quando "card" já existe)
    //   - extensões de palavras existentes (ex: "card" quando "car" já existe)
    // A solução é separar "criar o nó se não existe" de "decidir recursão ou marcar folha"
    private void inserir(String s, NoTrie no, int i) throws Exception {
        if(no.prox[s.charAt(i)] == null){
            no.prox[s.charAt(i)] = new NoTrie(s.charAt(i));
        }
        if(i == s.length() - 1){
            if(no.prox[s.charAt(i)].folha){
                throw new Exception("Palavra ja existe!");
            }
            no.prox[s.charAt(i)].folha = true;
        } else {
            inserir(s, no.prox[s.charAt(i)], i + 1);
        }
    }


    // --- MOSTRAR ---

    // Método público: inicia a exibição com string acumulada vazia e na raiz
    public void mostrar(){
        mostrar("", raiz);
    }

    // Método recursivo:
    //   curr = string acumulada até este nó
    //     → se o nó é a raiz ('\0'), não adiciona nada (evita lixo na string)
    //     → senão, concatena o caractere do nó atual
    //   se é folha → imprime a palavra completa acumulada em curr
    //   SEMPRE itera os filhos (sem if-else!) para não perder palavras que são
    //   prefixos de outras (ex: "car" e "card" coexistem na Trie)
    //
    // CORREÇÃO 1: original usava if-else, parando a recursão em nós folha e perdendo filhos
    // CORREÇÃO 2: original passava s + no.elemento incluindo '\0' da raiz em todas as palavras
    public void mostrar(String s, NoTrie no) {
        String curr = (no.elemento == '\0') ? s : s + no.elemento;
        if(no.folha){
            System.out.println("Palavra: " + curr);
        }
        for(int i = 0; i < no.prox.length; i++){
            if(no.prox[i] != null){
                mostrar(curr, no.prox[i]);
            }
        }
    }


    // --- CONTAR 'A' ---

    // Método público: verifica se a raiz existe antes de chamar o recursivo
    public int contarAs(){
        int resp = 0;
        if(raiz != null){
            resp = contarAs(raiz);
        }
        return resp;
    }

    // Método recursivo:
    //   conta 1 se o elemento do nó é 'A', 0 caso contrário
    //   SEMPRE itera os filhos (sem verificar folha!) para percorrer toda a Trie
    //
    // CORREÇÃO: o original verificava "if(no.folha == false)" antes de iterar filhos,
    // o que fazia parar em nós folha e perder os 'A' nas subárvores abaixo deles
    public int contarAs(NoTrie no) {
        int resp = (no.elemento == 'A') ? 1 : 0;
        for(int i = 0; i < no.prox.length; i++){
            if(no.prox[i] != null){
                resp += contarAs(no.prox[i]);
            }
        }
        return resp;
    }
}
