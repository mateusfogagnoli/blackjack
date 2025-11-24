# Blackjack (Java Swing)

Um jogo simples de 21 implementado em Java utilizando Swing para a interface gráfica.

## Regras

*   **Objetivo:** Obter uma pontuação de mão mais próxima de 21 do que o oponente, sem ultrapassar 21.
*   **Valores das Cartas:**
    *   **Ás (A):** Vale 1 ou 11 (o que for mais vantajoso para a mão).
    *   **Figuras (J, Q, K):** Valem 10.
    *   **Cartas Numéricas (2-10):** Valem o número impresso na carta.
*   **Jogabilidade:**
    *   O jogador e o oponente recebem duas cartas inicialmente.
    *   As cartas do jogador são visíveis. O oponente tem uma carta virada para cima e outra oculta.
    *   **Hit (Pedir):** Solicita mais uma carta para aumentar sua pontuação.
    *   **Stand (Parar):** Mantém a mão atual e encerra o turno.
    *   O oponente é obrigado a pedir cartas até atingir 17 ou mais.
*   **Vitória:**
    *   Se você ultrapassar 21, você "estoura" (Bust) e perde.
    *   Se o oponente ultrapassar 21, ele estoura e você ganha.
    *   Se ninguém estourar, a maior pontuação vence.
    *   Um empate resulta em "Push".

## Como Rodar

Certifique-se de ter o Java instalado no seu sistema.

1.  Abra um terminal (recomendado PowerShell no Windows) na pasta raiz do projeto.
2.  Execute o seguinte comando para compilar e iniciar o jogo:

```powershell
javac src/Card.java src/Deck.java src/Hand.java src/Blackjack.java; java -cp src Blackjack
```
