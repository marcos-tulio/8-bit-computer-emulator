# Emulador de um computador de 8 bits
 Este repositório contém o projeto de um emulador do computador de 8 bits desenvolvido por <a href="https://www.youtube.com/user/eaterbc/featured">Ben Eater</a>. 
 A elaboração deste conteúdo deu-se baseada na série <a href="https://www.youtube.com/watch?v=2llmPWBYvQo&list=PLZ8dBTV2_5HTB43Mhfz-TyIATkECrp8XY">"Construa um Computador de 8 Bits"</a> disponível no Youtube pelo canal <a href="https://www.youtube.com/channel/UCazAvTtoRlOrFDWDJDB2DKQ">WR Kits</a>.
 
## Computador de 8-bits
<p align="center">
<img width="700" src="https://github.com/mrcapybara/8-bit-computer-emulator/blob/master/git-resources/example-00.jpg"/>
</p>
 
## Instalação
```|
$ git clone https://github.com/marcos-tulio/8-bit-computer-emulator.git
$ java -jar 8-bit-computer-emulator/compiled/8-bit-computer-emulator.jar
```
 
## Como usar

Todos os comandos são executados via terminal interno obdecendo os seguintes critérios:

- Escritos em caixa baixa; 
- Os `valores_logicos` são: `true` ou `1` e `false` ou `0`;
- Os `valores_numericos` podem ser escritos em decimal ou em binário, acrescentando `b` ao seu início.

### Computador
Comando|Descrição|
-|-|
`run` |Inicia o computador
`stop`|Para o computador
`reset`|Volta o computador ao estado inicial: limpa os registros; não limpa a RAM
`asm`|Carrega um código em `Assembly` para a RAM -- compatível com o computador desenvolvido na série.
`clear`|Limpa o terminal (`Ctrl + l`)

### Registradores

Os comandos para os registradores deverão ser seguidos de parâmetros, obdecendo a seguinte notação:

`registrador parametro valor`

Exemplo:

`acc load b10101100`

Comando dos registradores:

Comando|Descrição|
-|-|
`acc` |Acumulador ou registrador A
`ins`|Registrador de instruções
`output`|Registrador de saída
`breg`|Registrador B

Parâmetros permitidos:

Parâmetro|Descrição|
-|-|
`registrador load valor_numerico`|Carrega o valor definido para o registrador.
`registrador reset`|Zera o registrador.
`registrador in valor_logico`|Habilita ou desabilita a entrada do registrador.
`registrador out valor_logico`|Habilita ou desabilita a saída do registrador.
