# Roadmap

**DRAFT** until Phase 0 is done. Android only until the app is reliable enough for daily use; the Windows app comes after that.

Every phase ends with a test gate. Claude runs what can be tested on the PC (unit tests, emulator); the maintainer tests on the real phone.

## Phase 0: Discovery (current)

- [x] Vision and core features agreed, written into `FEATURES.md`
- [x] AI backend approach decided
- [x] Tech stack confirmed
- [x] Project name decided
- [x] Official Claude app ruled out as an alternative
- [x] Public GitHub repo created, license picked, notes committed

**Gate:** every open question in `DECISIONS.md` is answered or explicitly postponed.

## Phase 1: Foundations

- [ ] Gradle project in Kotlin, SDK platform 37 installed, works with the existing JDK
- [ ] Core module without Android dependencies, so a PC app can reuse it later
- [ ] Empty app builds and starts on the Nothing Phone
- [ ] `.gitignore` that keeps secrets and build output out of the repo
- [ ] GitHub Actions builds the APK on every push

**Gate:** the empty app runs on the phone, built from a clean checkout and by GitHub Actions.

## Phase 2: Text chat on Android

- [ ] Settings: API key stored securely, model picker filled from the Models API
- [ ] Type a question, get an answer from Claude
- [ ] Clear messages for no key, wrong key, no internet, no credits

**Gate:** a real conversation works; every error case shows a message instead of crashing.

## Phase 3: Voice and long-press, first release

- [ ] Speech to text and text to speech in German
- [ ] Selectable as the default digital assistant, long-press opens it
- [ ] Reacts reliably when the user stops talking, never drops a request silently
- [ ] First GitHub Release, v0.1.0

**Gate:** long-press, ask by voice, hear the answer. Fresh install from the release page works.

## Phase 4: First actions

- [ ] Time and date
- [ ] Timers and alarms
- [ ] Weather
- [ ] Open apps
- [ ] Calendar: read and create events
- [ ] Context block (time, place, weather) sent with every request

**Gate:** every action has its own test and works by voice on the phone. "What can I do now?" gives an answer that fits weather and place.

## Phase 5: Notifications and messages

- [ ] Read incoming notifications and messages
- [ ] Per-app permission setting, blocked apps reported to the AI as disabled

**Gate:** "what did X write me" works; a blocked app's content never reaches the AI.

## Phase 6: Memory

- [ ] Remembers preferences across conversations, stored on the phone
- [ ] Profile and setup on first start (fields from `DECISIONS.md`)
- [ ] One-time import (Claude memory export, own notes, writing style examples)
- [ ] User can see and delete what it remembers; export for a new phone

**Gate:** a preference set in one conversation is used in a later one after an app restart.

## Phase 7: Replies in the user's style

- [ ] "Reply to that": Wisp writes the message in the user's style, reads it back, sends after "yes"

**Gate:** replies sent through WhatsApp, SMS and Discord; nothing is sent without confirmation.

## Phase 8: Daily-driver polish

- [ ] Gemini fallback
- [ ] "Open a new Claude chat"
- [ ] A week of daily use with Google Assistant off, every problem fixed

**Gate:** the maintainer would not go back to Google Assistant. Release v1.0.0.

## Later

Everything else from `FEATURES.md` (Windows app and sync, PC control, smart home via Home Assistant, wake word, English), ordered when we get there.
