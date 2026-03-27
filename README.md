# 🚀 TaskFlow — Sistema de Gestão de Tarefas

## 📖 Visão Geral

O **TaskFlow** é uma aplicação web fullstack desenvolvida para gerenciamento de tarefas pessoais e em equipe, com foco em produtividade e organização.

### 🎯 Objetivo
Permitir que usuários criem, organizem e acompanhem suas tarefas de forma eficiente.

### ❗ Problema Resolvido
Substitui métodos desorganizados (anotações, mensagens, etc.) por um sistema centralizado, melhorando:
- Controle de tarefas
- Produtividade
- Gestão de prazos

### 👥 Público-alvo
- Estudantes  
- Desenvolvedores  
- Freelancers  
- Pequenas equipes  

---

## 🧩 Requisitos do Sistema

### ✅ Funcionais
- Cadastro de usuários  
- Login e autenticação  
- Criar, editar e excluir tarefas  
- Listar tarefas do usuário  
- Marcar tarefas como concluídas  
- Definir prioridade (LOW, MEDIUM, HIGH)  
- Definir prazo (deadline)  
- Filtrar tarefas  
- Dashboard com resumo  

---

### ⚙️ Não Funcionais
- Tempo de resposta < 2s  
- Sistema responsivo  
- Segurança com autenticação JWT  
- Código escalável e organizado  
- API REST padronizada  
- Banco relacional normalizado  

---

## 🗂️ Modelagem do Sistema

### 🧱 Entidades

#### 👤 User
- id  
- name  
- email  
- password  

#### 📌 Task
- id  
- title  
- description  
- status (PENDING, DONE)  
- priority (LOW, MEDIUM, HIGH)  
- deadline  
- createdAt  
- user_id  

---

### 🔗 Relacionamentos
- Um usuário pode ter várias tarefas (1:N)

---

## 🧠 Regras de Negócio
- Email deve ser único  
- Senha deve ser criptografada  
- Usuário só acessa suas próprias tarefas  
- Não pode editar tarefas de outros usuários  
- Título da tarefa é obrigatório  
- Status padrão: PENDING  

---

## 🏗️ Arquitetura do Projeto

### 📦 Backend
src/main/java/com/taskflow
├── controller
├── service
├── repository
├── entity
├── dto
├── config
└── security

### 🎨 Frontend
/frontend
├── index.html
├── login.html
├── register.html
├── dashboard.html
├── /css
└── /js

---

## 🔌 API REST

### 🔐 Autenticação

POST /auth/register
POST /auth/login

### 📌 Tarefas

GET /tasks  
POST /tasks  
PUT /tasks/{id}  
DELETE /tasks/{id}  
PATCH /tasks/{id}/status  

---

## 🛢️ Banco de Dados (MySQL)

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    status VARCHAR(20),
    priority VARCHAR(20),
    deadline DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

---

## 🔐 Segurança
- JWT  
- Spring Security  
- BCrypt  

---

## 🚀 Etapas
1. Base (Spring + MySQL)  
2. Backend (CRUD)  
3. Segurança (JWT)  
4. Frontend  
5. Integração  
6. Refinamento  

---

## 📌 Tecnologias
- Java + Spring Boot  
- MySQL  
- HTML, CSS, JavaScript  

---

## 📈 Objetivo
Projeto fullstack para portfólio demonstrando boas práticas e arquitetura profissional.
