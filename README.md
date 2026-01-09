# Pokédex Android App

Aplicativo Android nativo desenvolvido em **Kotlin**, que consome a **PokeAPI** para listar e exibir detalhes de Pokémon.  
O aplicativo também conta com um sistema simples de **autenticação de usuários**, incluindo cadastro, login, perfil e logout, utilizando armazenamento local.

---

## Visão Geral

Este projeto tem como objetivo demonstrar o consumo de uma **API REST pública**, organização básica de um aplicativo Android e navegação entre múltiplas telas, seguindo boas práticas de desenvolvimento mobile.

---

## Funcionalidades

- Cadastro de usuários
- Login de usuários
- Listagem de Pokémon via PokeAPI
- Exibição de detalhes completos de cada Pokémon
- Perfil do usuário
- Logout

---

## API REST Utilizada

### **PokeAPI**
API pública e gratuita com informações completas sobre Pokémon.

- Site oficial: https://pokeapi.co/

### **Endpoints Consumidos**

| Método | Endpoint | Descrição |
|------|---------|-----------|
| GET | `https://pokeapi.co/api/v2/pokemon` | Retorna a lista de Pokémon |
| GET | `https://pokeapi.co/api/v2/pokemon/{name}` | Retorna os detalhes de um Pokémon específico |

---

## Tecnologias Utilizadas

- **Kotlin** – Linguagem principal
- **Retrofit 2** – Consumo de API REST
- **Picasso** – Carregamento e exibição de imagens
- **Material Design** – Interface moderna e padronizada
- **Android SDK** – Desenvolvimento nativo

---

## Arquitetura

- Estrutura baseada em **Activities**
- Separação de responsabilidades:
  - `model` → Modelos de dados
  - `network` → Comunicação com a API
  - `AuthManager` → Gerenciamento de autenticação
- Comunicação com API via **Retrofit**
- Persistência local simples para usuários

---

## Estrutura do Projeto

```text
com.example.apirest/
├── model/
│   ├── PokemonDetailResponse.kt
│   ├── PokemonResponse.kt
│   └── User.kt
├── network/
│   └── ApiService.kt
├── AuthManager.kt
├── LoginActivity.kt
├── PokemonListActivity.kt
├── PokemonDetailActivity.kt
└── ProfileActivity.kt
```

## Telas do Aplicativo

### LoginActivity
- Cadastro de usuários
- Login de usuários

### PokemonListActivity
- Lista de Pokémon consumida da PokeAPI

### PokemonDetailActivity
- Exibição dos detalhes completos do Pokémon selecionado

### ProfileActivity
- Exibição do perfil do usuário
- Opção de logout

---

## Requisitos Atendidos

- Consumo de API REST externa
- Cadastro e autenticação de usuários
- Aplicação com múltiplas telas
- Uso de bibliotecas modernas (Retrofit e Picasso)
- Interface seguindo os padrões do Material Design

---

## Como Executar o Projeto

1. Clone o repositório: https://github.com/RobertoSergio/API-Rest.git
2. Abra o projeto no Android Studio
3. Aguarde a sincronização do Gradle
4. Execute o aplicativo em um emulador ou dispositivo físico

## Link do Vídeo Demonstração da execução da aplicação

https://drive.google.com/file/d/1SKSs8sQ9iVhabl_KrXjZmu8JurUqhspT/view
