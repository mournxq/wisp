# Wisp

Open-source AI alternative to Google Assistant, powered by Claude. Android app first (Kotlin, Jetpack Compose), Windows app later. Anyone can install it from GitHub Releases, no app store.

## Roles

- Claude writes the code, keeps the notes in this repo current, and runs and tests everything before calling it done.
- The maintainer decides what gets built, tests on real devices, and gives feedback. They are learning to code through this project: explain on request, teach git as it comes up.

## Where things live

- `CLAUDE.md` (this file): project brain, loaded automatically every session. Keep it short, details go in `docs/`.
- `docs/FEATURES.md`: feature wishlist with checkboxes. Any "I want X someday" goes in there right away.
- `docs/ROADMAP.md`: the build plan, phase by phase, current phase marked.
- `docs/DECISIONS.md`: what was decided, when and why, plus open questions and research notes.
- `README.md`: the public README for GitHub. Not written yet, and only written together with the maintainer.

## Current status

- Phase 0 done. Repo: https://github.com/mournxq/wisp (public, MIT). Nothing built yet.
- Next: Phase 1 in `docs/ROADMAP.md`. Plan it with the maintainer before writing code.
- The maintainer dictates by voice: "Cloud" in their messages usually means Claude.

## Project rules

- No API keys, tokens or personal data in the repo, ever. It will be public.
- English for code, docs and commit messages.
- When something is decided or finished, update `docs/` in the same session.
- New feature wishes go into `docs/FEATURES.md` immediately, even vague ones.
- Every roadmap phase ends with a test gate. A phase is done when the gate passes, not when the code compiles.
