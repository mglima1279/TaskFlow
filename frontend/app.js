const url = "http://localhost:5050"

const getAuthHeaders = () => {
    const token = localStorage.getItem("token");
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
    };
};

document.getElementById("form-register").addEventListener("submit", async e => {
    e.preventDefault()

    const formData = new FormData(e.target)

    const info = {
        name: formData.get("name"),
        email: formData.get("email"),
        password: formData.get("password")
    }

    try {
        const response = await fetch(url + "/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(info)
        });

        if (response.ok) {
            const token = await response.text();

            console.log("Response: " + token)
            localStorage.setItem("token", token);
        } else {
            console.error("Erro no cadastro:", response.status);
        }
    } catch (e) {
        console.error("Erro na requisição:", e);
    }
})

document.getElementById("form-login").addEventListener("submit", async e => {
    e.preventDefault()

    const formData = new FormData(e.target)

    const info = {
        email: formData.get("email"),
        password: formData.get("password")
    }

    try {
        const response = await fetch(url + "/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(info)
        })

        if (response.ok) {
            const token = await response.text()

            console.log("Response: " + token)
            localStorage.setItem("token", token)
        } else {
            console.error("Erro no cadastro:", response.status)
        }
    } catch (e) {
        console.error("Erro na requisição:", e)
    }
})

document.getElementById("form-get-me").addEventListener("submit", async e => {
    e.preventDefault()

    const token = localStorage.getItem("token")

    if (!token) {
        return
    }

    try {
        const response = await fetch(url + "/users/me", {
            method: "GET",
            headers: getAuthHeaders()
        })

        if (response.ok) {
            const user = await response.json()

            console.log("Response: ", user)
        } else {
            console.error("Erro no cadastro:", response.status)
        }
    } catch (e) {
        console.error("Erro na requisição:", e)
    }
})


document.getElementById("form-create-task").addEventListener("submit", async e => {
    e.preventDefault();
    const formData = new FormData(e.target);

    const info = {
        title: formData.get("title"),
        description: formData.get("description"),
        priority: formData.get("priority"),
        deadline: formData.get("deadline")
    };

    try {
        const response = await fetch(url + "/tasks", {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(info)
        });

        if (response.ok) {
            const data = await response.json();
            console.log("Tarefa criada:", data);
        } else {
            console.error("Erro ao criar tarefa:", response.status);
        }
    } catch (e) {
        console.error("Erro na requisição:", e);
    }
});

document.getElementById("form-list-tasks").addEventListener("submit", async e => {
    e.preventDefault();

    try {
        const response = await fetch(url + "/tasks", {
            method: "GET",
            headers: getAuthHeaders()
        });

        if (response.ok) {
            const tasks = await response.json();
            console.log("Lista de Tarefas:", tasks);
        } else {
            console.error("Erro ao listar tarefas:", response.status);
        }
    } catch (e) {
        console.error("Erro na requisição:", e);
    }
});

document.getElementById("form-get-task").addEventListener("submit", async e => {
    e.preventDefault();
    const id = new FormData(e.target).get("taskId");

    try {
        const response = await fetch(`${url}/tasks/${id}`, {
            method: "GET",
            headers: getAuthHeaders()
        });

        if (response.ok) {
            const task = await response.json();
            console.log("Tarefa encontrada:", task);
        } else {
            console.error("Tarefa não encontrada ou erro:", response.status);
        }
    } catch (e) {
        console.error("Erro na requisição:", e);
    }
});

document.getElementById("form-delete-task").addEventListener("submit", async e => {
    e.preventDefault();
    const id = new FormData(e.target).get("taskId");

    try {
        const response = await fetch(`${url}/tasks/${id}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });

        if (response.ok) {
            console.log(`Tarefa ${id} deletada.`);
        } else {
            console.error("Erro ao deletar:", response.status);
        }
    } catch (e) {
        console.error("Erro na requisição:", e);
    }
});

document.getElementById("form-update-task").addEventListener("submit", async e => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const id = formData.get("taskId");

    const info = {
        title: formData.get("title"),
        description: formData.get("description"),
        priority: formData.get("priority"),
        deadline: formData.get("deadline")
    };

    try {
        const response = await fetch(`${url}/tasks/${id}`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify(info)
        });

        if (response.ok) {
            const data = await response.json();
            console.log("Tarefa atualizada:", data);
        } else {
            console.error("Erro na atualização:", response.status);
        }
    } catch (e) {
        console.error("Erro na requisição:", e);
    }
});

document.getElementById("form-patch-status").addEventListener("submit", async e => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const id = formData.get("taskId");
    const status = formData.get("status");

    try {
        const response = await fetch(`${url}/tasks/${id}/status`, {
            method: "PATCH",
            headers: getAuthHeaders(),
            body: JSON.stringify({ status: status })
        });

        if (response.ok) {
            const data = await response.json();
            console.log("Status atualizado:", data);
        } else {
            console.error("Erro no patch de status:", response.status);
        }
    } catch (e) {
        console.error("Erro na requisição:", e);
    }
});
