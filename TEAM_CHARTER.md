# ByteBloomAcademy ShinobiCoreTeam

Core Shinobi is the elite team within ByteBloom Academy, combining Core, which represents the heart of the system, the foundation of programming, and the depth of software engineering, with Shinobi, which reflects skill, intelligence, speed, and the ability to break through the toughest problems with creative solutions.

---

## 1. Workflow Architect

The Workflow Architect serves as the organizational backbone of the ShinobiCoreTeam, responsible for designing the infrastructure that ensures smooth project workflow across all four team members.

### Core Responsibilities
* **Team Role Assignment:** The Workflow Architect assigns each team member as a guardian of a specific domain, ensuring clear distribution of responsibilities and preventing task overlap across the team.
* **Branching Model:** The Workflow Architect adopts the GitHub Flow simplified model, keeping the main branch always production-ready while creating dedicated feature branches for each team member. Direct commits to main are prohibited, and branches are deleted after successful merging.
* **Commit Message Standards:** The Workflow Architect establishes a strict commit message system using standardized prefixes including `feat`, `fix`, `docs`, `style`, `refactor`, `test`, and `chore`. Each commit must clearly describe what was changed and why.
* **Pull Request Workflow:** The Workflow Architect implements a structured PR process requiring descriptive titles, mandatory peer review from at least two team members, and the Squash and Merge strategy to maintain a clean commit history on the main branch.
* **Conflict Resolution Protocol:** The Workflow Architect designs a clear protocol for handling merge conflicts, emphasizing immediate team communication, pulling the latest changes from main, resolving conflicts locally, and re-requesting reviews from all approvers.
* **Tools and Technologies:** The Workflow Architect utilizes Git for local version control, GitHub for repository hosting and branch management, and Google Meet or WhatsApp for quick team communication and conflict discussions.
* **Acceptance Criteria:** The Workflow Architect is responsible for creating the dedicated workflow branch, documenting the complete workflow plan, pushing the branch to GitHub, opening a Pull Request, obtaining approvals from two team members, and successfully merging the changes to the main branch.

### Summary
The Workflow Architect establishes the foundational rules that ensure speed through simplified branching, quality through mandatory peer review, transparency through standardized commit messages, and professionalism through clear conflict resolution protocols.

---

## 2. Clean Code Standards

Responsible for defining clean code standards to ensure consistency, readability, and maintainability across the team's codebase.

### Naming Conventions
* **Variables:** `camelCase` (e.g., `userAge`, `isLoggedIn`)
* **Functions:** `camelCase`, verb-based (e.g., `calculateTotalPrice`)
* **Classes:** `PascalCase` (e.g., `UserRepository`)
* **Constants:** `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY_COUNT`)

### Formatting Rules
* **Indentation:** 4 spaces (no tabs)
* **Max Line Length:** 120 characters
* **Variables Preference:** Prefer `val` over `var`
* **Control Flow:** Use `when` instead of long `if-else` chains

### Function Design
* One function should do one thing only.
* Use named arguments for functions with 3 or more parameters.
* Keep functions short, focused, and highly cohesive.

### Comments & Documentation
* Explain **why** the code does something, not **how**.
* Use clear and concise KDoc/documentation for all public functions.

---

## 3. Communication & SLAs

### Internal Communication Protocols
* We use **Google Meet** and **WhatsApp** for our daily team chat, syncs, and quick updates.
* We use **GitHub Issues** and **Pull Request comments** exclusively for technical discussions and code reviews.

### Daily Stand-up Meetings
* We will have a short 15-minute meeting every day at **12:00 AM**.
* Each member will quickly answer three key questions:
    1. What did I do yesterday?
    2. What will I do today?
    3. Do I have any blockers or problems?

### Peer Review SLAs (Service Level Agreements)
* When a team member opens a Pull Request (PR), it must be reviewed and feedback provided within **24 hours**.
* We strictly require at least **two approvals** from teammates before any code is merged into the `main` branch.

---

## 4. Architecture & Ignores

### Target Directory Models
Since we are building a Kotlin project, we will follow a clean architecture folder structure:

```text
src/
└── main/
    └── kotlin/
        ├── packages/     # Main domain models, configurations, and core business logic
        └── usecases/     # Specific actions, operations, and business rules for LogiRoute
src/
└── test/                 # Strictly reserved for Kotlin unit tests