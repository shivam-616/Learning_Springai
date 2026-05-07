    const apiStatus = document.querySelector("#apiStatus");
const uploadForm = document.querySelector("#uploadForm");
const askForm = document.querySelector("#askForm");
const deleteForm = document.querySelector("#deleteForm");
const clearAnswer = document.querySelector("#clearAnswer");
const answerBox = document.querySelector("#answerBox");
const uploadResult = document.querySelector("#uploadResult");
const deleteResult = document.querySelector("#deleteResult");

const setStatus = (message, busy = false) => {
    apiStatus.textContent = message;
    document.querySelectorAll("button").forEach((button) => {
        button.disabled = busy;
    });
};

const showMessage = (element, message, isError = false) => {
    element.textContent = message;
    element.style.color = isError ? "#b94235" : "#637068";
};

const requestText = async (url, options = {}) => {
    const response = await fetch(url, options);
    const text = await response.text();

    if (!response.ok) {
        throw new Error(text || `Request failed with status ${response.status}`);
    }

    return text;
};

const escapeHtml = (value) => String(value ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");

const renderSearchResults = (items) => {
    if (!Array.isArray(items) || items.length === 0) {
        answerBox.innerHTML = '<p class="muted">No matching notes were found.</p>';
        return;
    }

    answerBox.innerHTML = items.map((item, index) => {
        const metadata = item.metadata || {};
        const source = metadata.source || metadata.fileName || metadata.filename || "Unknown source";
        const content = item.text || item.content || item.formattedContent || JSON.stringify(item, null, 2);

        return `
            <section class="search-result">
                <div class="meta-row">Result ${index + 1} · ${escapeHtml(source)}</div>
                <p>${escapeHtml(content)}</p>
            </section>
        `;
    }).join("");
};

const renderInsight = (insight) => {
    answerBox.innerHTML = `
        <h3>${escapeHtml(insight.conceptName || "Study insight")}</h3>
        <p><strong>Complexity:</strong> ${escapeHtml(insight.complexityLevel || "Not provided")}</p>
        <p><strong>Memory trick:</strong> ${escapeHtml(insight.memoryTrick || "Not provided")}</p>
        <h3>Key definitions</h3>
        <ul>
            ${(insight.keyDefinitions || []).map((definition) => `<li>${escapeHtml(definition)}</li>`).join("")}
        </ul>
    `;
};

uploadForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    setStatus("Uploading", true);
    showMessage(uploadResult, "");

    try {
        const formData = new FormData(uploadForm);
        const message = await requestText("/api/ingest/notes", {
            method: "POST",
            body: formData
        });

        showMessage(uploadResult, message);
        setStatus("Upload complete");
        uploadForm.reset();
    } catch (error) {
        showMessage(uploadResult, error.message, true);
        setStatus("Upload failed");
    }
});

askForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const subject = document.querySelector("#askSubject").value.trim();
    const query = document.querySelector("#query").value.trim();
    const mode = document.querySelector("#answerMode").value;
    const params = new URLSearchParams({ subject, query });

    setStatus("Thinking", true);
    answerBox.innerHTML = '<p class="muted">Working on it...</p>';

    try {
        if (mode === "search") {
            const response = await fetch(`/api/search?${params}`);
            if (!response.ok) {
                throw new Error(await response.text());
            }
            renderSearchResults(await response.json());
        } else if (mode === "insight") {
            const response = await fetch(`/api/insit?${params}`);
            if (!response.ok) {
                throw new Error(await response.text());
            }
            renderInsight(await response.json());
        } else {
            const answer = await requestText(`/api/rag/ask?${params}`);
            answerBox.textContent = answer;
        }

        setStatus("Ready");
    } catch (error) {
        answerBox.innerHTML = `<p class="muted">${escapeHtml(error.message)}</p>`;
        setStatus("Request failed");
    }
});

deleteForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const subject = document.querySelector("#deleteSubject").value.trim();

    setStatus("Deleting", true);
    showMessage(deleteResult, "");

    try {
        const message = await requestText(`/api/delete/notes?${new URLSearchParams({ subject })}`, {
            method: "DELETE"
        });

        showMessage(deleteResult, message);
        setStatus("Deleted");
        deleteForm.reset();
    } catch (error) {
        showMessage(deleteResult, error.message, true);
        setStatus("Delete failed");
    }
});

clearAnswer.addEventListener("click", () => {
    answerBox.innerHTML = '<p class="muted">Your answer, search results, or structured insight will appear here.</p>';
    setStatus("Ready");
});
