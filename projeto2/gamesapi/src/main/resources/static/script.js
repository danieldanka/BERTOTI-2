const API = "http://localhost:8080/games";

let selectedId = null;

function loadGames() {
    fetch(API)
        .then(res => res.json())
        .then(data => {
            const table = document.getElementById("table-body");
            table.innerHTML = "";

            data.forEach(game => {
                table.innerHTML += `
                    <tr>
                        <td>${game.id}</td>
                        <td>${game.name}</td>
                        <td>${game.genre}</td>
                        <td>${game.platform}</td>
                        <td>${game.releaseYear}</td>
                        <td>
                            <button onclick="selectGame(${game.id}, '${game.name}', '${game.genre}', '${game.platform}', ${game.releaseYear})">Editar</button>
                            <button onclick="deleteGame(${game.id})">Excluir</button>
                        </td>
                    </tr>
                `;
            });
        });
}

function addGame() {
    const game = getFormData();

    fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(game)
    }).then(() => loadGames());
}

function updateGame() {
    if (!selectedId) return;

    const game = getFormData();

    fetch(`${API}/${selectedId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(game)
    }).then(() => {
        selectedId = null;
        loadGames();
    });
}

function deleteGame(id) {
    fetch(`${API}/${id}`, { method: "DELETE" })
        .then(() => loadGames());
}

function selectGame(id, name, genre, platform, year) {
    selectedId = id;

    document.getElementById("name").value = name;
    document.getElementById("genre").value = genre;
    document.getElementById("platform").value = platform;
    document.getElementById("year").value = year;
}

function getFormData() {
    return {
        name: document.getElementById("name").value,
        genre: document.getElementById("genre").value,
        platform: document.getElementById("platform").value,
        releaseYear: parseInt(document.getElementById("year").value)
    };
}

window.onload = loadGames;