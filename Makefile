# Variáveis
JAVAC = javac
JAVA = java
LIBS = lib/jackson-databind-2.15.2.jar:lib/jackson-core-2.15.2.jar:lib/jackson-annotations-2.15.2.jar
SRC_DIR = visao util controle modelo # Diretórios de origem
BIN_DIR = bin
JFLAGS = -cp $(LIBS) -d $(BIN_DIR)
RUNFLAGS = -cp $(BIN_DIR):$(LIBS)

# Caminhos dos arquivos fonte
SRC = src/*/*

# Regras principais
all: clean build run

build:
	@echo "Compilando o projeto..."
	mkdir -p $(BIN_DIR)
	$(JAVAC) $(JFLAGS) $(SRC)
	@echo "Compilação concluída com sucesso!"

run:
	@echo "Executando o projeto..."
	$(JAVA) $(RUNFLAGS) visao.Principal

clean:
	@echo "Limpando os arquivos gerados..."
	rm -rf $(BIN_DIR)

# Adicional: Verifica a estrutura dos diretórios e arquivos
check:
	@echo "Verificando estrutura de arquivos..."
	@for dir in 'src/${SRC_DIR}'; do \
		if [ ! -d "$$dir" ]; then \
			echo "Erro: Diretório $$dir não encontrado."; \
			exit 1; \
		fi; \
	done
	@echo "Estrutura de diretórios verificada com sucesso."
