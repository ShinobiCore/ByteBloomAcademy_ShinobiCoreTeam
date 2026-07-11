# ByteBloomAcademy ShinobiCoreTeam

Core Shinobi is the elite team within ByteBloom Academy, combining Core, which represents the heart of the system, the foundation of programming, and the depth of software engineering, with Shinobi, which reflects skill, intelligence, speed, and the ability to break through the toughest problems with creative solutions.
## Clean Code Standards

Responsible for defining clean code standards to ensure consistency, readability, and maintainability across the team's
codebase.

### Naming Conventions

- Variables: camelCase (example: userAge, isLoggedIn)
- Functions: camelCase, verb-based (example: calculateTotalPrice)
- Classes: PascalCase (example: UserRepository)
- Constants: UPPER_SNAKE_CASE (example: MAX_RETRY_COUNT)

### Formatting Rules

- Indentation: 4 spaces (no tabs)
- Max line length: 120 characters
- Prefer val over var
- Use when instead of long if-else chains

### Function Design

- One function should do one thing only
- Use named arguments for functions with 3 or more parameters
- Keep functions short and focused

### Comments

- Explain why the code does something, not how
- Use clear documentation for public functions

### Goal

Ensure that all code written by the team is unified, readable, and easy to maintain.


### Communication & SLAs

**1. Internal Communication Protocols:**

* We use [goole meet - WhatsApp] for our daily team chat and quick updates.
* We use GitHub Issues and Pull Requests comments for technical discussions.

**2. Daily Stand-up Meetings:**

* We will have a short 15-minute meeting every day at  12:00 َAM.
* Each member will quickly answer three questions:
1. What did I do yesterday?
2. What will I do today?
3. Do I have any blockers or problems?

**3. Peer Review SLAs (Service Level Agreements):**

* When a team member opens a Pull Request (PR), it must be reviewed within **24 hours**.
* We strictly require at least **two approvals** from teammates before any code is merged into the `main` branch.

---

### Architecture & Ignores

**1. Target Directory Models:**
Since we are building a Kotlin project, we will follow a clean architecture folder structure:

* `src/main/kotlin/packages/`: This folder will contain our main domain models, configurations, and core logic.
* `src/main/kotlin/usecases/`: This folder will contain the specific actions, operations, and business rules for the LogiRoute system.
* `src/test/`: This folder is strictly for our Kotlin unit tests.

**2. .gitignore Exclusions:**
To keep our repository clean and avoid merge conflicts with local settings, our `.gitignore` will block the following files:

* **Gradle Files:** `.gradle/`, `build/` (These are generated automatically).
* **IDE Files:** `.idea/`, `*.iml` (IntelliJ IDEA or Android Studio local workspace settings).
* **Local Configs:** `local.properties` (Contains local device paths or private keys that should not be shared).
---

Core Shinobi is the elite team within ByteBloom Academy, combining Core, which represents the heart of the system, the
foundation of programming, and the depth of software engineering, with Shinobi, which reflects skill, intelligence,
speed, and the ability to break through the toughest problems with creative solutions.

1. Development & Database Environment
* **Language & Framework:** Kotlin (Clean Architecture)
* **Database Management:** MS SQL Server & SQLite for local/testing environments.
* **IDE & Tools:** IntelliJ IDEA & Git.