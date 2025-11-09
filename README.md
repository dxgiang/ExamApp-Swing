# ExamApp‑Swing

A simple desktop exam application built with Java Swing. Designed as a learning project, it features:

- Login/Register system with:
  - Account creation and password validation
  - Lockout after 3 failed login attempts (5-second freeze)
- **Root** (admin) mode that allows:
  - Adding/deleting users
  - Viewing user list & locking/unlocking accounts
- **Exam mode**:
  - Prevents exit or window switching until the test is completed
  - Anti-cheat detection: minimizing, alt‑tabbing, or clicking outside triggers a warning

> **Note:** This is a learning version—the UI is minimal and English may be imperfect. =))

---

## 🛠️ Features

1. **User Authentication**
   - Secure login/register flow
   - Lockout mechanism after repeated failed attempts
2. **Admin (Root) Console**
   - Full user management (add, delete, lock/unlock)
   - View current users and their status
3. **Exam Interface**
   - Focus‑only mode: exits are blocked until the test ends
   - Anti-cheating alerts for window-switch attempts

---

## 🧩 Prerequisites

- Java JDK 8 or higher
- Git (for cloning)

---

## 🚀 Installation

```bash
git clone https://github.com/dxgiang/ExamApp-Swing
cd ExamApp-Swing
```
---
## Diagram
Click [here](https://www.mermaidchart.com/d/6598724a-e615-4ba3-9f90-4200e8ae23c7) to see the diagram
