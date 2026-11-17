# Decisions

Newest entries on top in each section. Format: date, decision, reason.

## Decided

- **2026-11-17** License: MIT. Public repo `mournxq/wisp` on GitHub, branch `main`.
- **2026-11-17** The official Claude app is not an option as the assistant; the maintainer knows it well and it does not do the job. Wisp gets built with its full scope.
- **2026-11-17** Context block with every request: time, place name and current weather, kept small. Location is the last known position (no GPS fix per request, saves battery); weather cached for 30 minutes so it does not slow down answers. Exact coordinates only go out when a request needs them (navigation, places nearby).
- **2026-11-17** Profile stores the birth date, not the age. Profile and writing style are their own memory sections the user can see and edit.
- **2026-11-17** Name: **Wisp**. Kobold was the other candidate but clashes with KoboldAI, a well-known local AI project (about 4,000 stars). Wake word later as "Hey Wisp", since one syllable alone triggers too easily.
- **2026-11-17** Android only for now, Windows app postponed. Stack: plain Android in Kotlin with Jetpack Compose. The AI client, memory and actions live in a module without Android dependencies so a PC app can reuse them later. Kotlin Multiplatform dropped as too much build overhead for one platform.
- **2026-11-17** Model is picked in the settings, filled live from Anthropic's Models API, so new models show up without an app update. Default Haiku.
- **2026-11-17** Gemini only as a fallback, not as an equal provider.
- **2026-11-17** Smart home through Home Assistant (planned self-hosted on the homelab), not Google Home. Waits until Home Assistant runs.
- **2026-11-17** Message replies use the reply button that apps put in their notifications (Android `RemoteInput`). Works for every app that offers one (WhatsApp, SMS, Discord, Instagram); apps without it cannot be answered this way. Wisp writes the reply in the user's style instead of dictating word for word, and reads it back for confirmation before sending (default, can be switched off).
- **2026-11-17** The claude.ai account (memory, past chats) cannot be accessed by third-party apps. Wisp has its own memory, seeded once from a Claude memory export and the maintainer's own notes. "Open a new Claude chat" hands over to the official Claude app.
- **2026-11-17** Target phone: Nothing Phone, Nothing OS 5, Android 17.
- **2026-11-17** Memory is stored on the device first. Sync between devices comes later and is optional. Reason: memory does not need a server, only sharing it does.
- **2026-11-17** Notification privacy is a per-app setting. The maintainer's own setup allows everything.
- **2026-11-17** Audience: the maintainer plus people who can follow a short API key setup. Not built for non-technical users.
- **2026-11-17** German first, built so English can be added (no hard-coded texts, voice language is a setting).
- **2026-11-17** Started by long-press on Android. Wake word later. Configurable hotkey on Windows. Mainly voice on the phone, text allowed on the PC.
- **2026-11-17** AI: Claude Haiku with the user's own API key. A provider layer from day one so a fallback (Gemini free tier) can be added later. Other providers only if needed. Rejected: one central server with the maintainer's key for everyone (pays for strangers, abuse, accounts, GDPR responsibility). Rejected as main brain: local models on the phone or the CPU-only homelab (too weak or too slow).
- **2026-11-17** No "connect your Claude account" login. Anthropic does not let third-party apps use a Pro/Max subscription login; the API key is the way in.
- **2026-11-17** Android and Windows are the first platforms. Android first, the Windows app comes later.
- **2026-11-17** Distribution through GitHub Releases, no app store. Repo will most likely be public.
- **2026-11-17** Claude writes the code; the maintainer decides, tests and gives feedback.
- **2026-11-17** Project notes live in the repo: `CLAUDE.md` plus `docs/`.

## Proposed, waiting for OK

- **To verify in Phase 1:** Anthropic's official Java SDK on Android (Kotlin can use it), and Gradle with the installed JDK 25. Android 17 needs SDK platform 37, only 36.1 is installed.
- **Sync, for when the PC app comes:** the PC app is the sync partner, optionally the homelab. Phone works fully on its own. Pairing once with a code. Open problem: reaching the PC from mobile data (Tailscale or similar).
- **Easy reinstall on a new phone:** APK from GitHub Releases, built by GitHub Actions. Settings and memory exportable to a file so a new phone starts where the old one stopped.
- **Calendar through Android's own calendar store** (`CalendarContract`), which already holds the synced Google Calendar. No Google login or Google Cloud project needed.
- **Weather from Open-Meteo**: free, no API key.

## Open questions

1. Which profile fields the setup asks for: proposed name, birth date, home town, languages, short "about me" text, writing style examples. To confirm before Phase 6.

## Research notes

- **2026-11-17 Official Claude Android app:** per the Claude help center it sets alarms and timers, reads and creates calendar events, uses location and Maps, and drafts messages for WhatsApp and others, which the user sends by tapping. It does not read existing messages. Default-assistant support is not documented; a user-filed GitHub issue (anthropics/claude-code#41696) mentions the official app holding the assistant role. Claude Duet (launched October 2026) is a separate agentic app, waitlist, Pro/Max, US.
- **2026-11-17 Claude memory:** claude.ai memory and chats have no API for third-party apps. Memory can be exported (help center: import and export your memory).

- **2026-11-17 Android default assistant:** an app becomes selectable under Settings, Apps, Default apps, Digital assistant app by declaring a `VoiceInteractionService` (with a `RecognitionService`) or an `ACTION_ASSIST` activity. Only the default app stays bound and receives the assist gesture. Source: AOSP voice interaction guide.
- **2026-11-17 Claude API prices:** Haiku 5.5 $0.10 / $0.50 per million input/output tokens, Sonnet 5.5 $2 / $10, Opus 5.5 $4 / $20. Rough estimate at 50 requests a day (about 2,000 tokens in, 300 out each): Haiku about $0.50 a month, Sonnet about $10. Haiku 5.5 has no server-side refusal fallback, so a fallback provider has to be handled in the app.
- **2026-11-17 Gemini free tier:** exists, but limits are only reliably shown in Google AI Studio and were reportedly cut hard in December 2025. Free-tier prompts may be used by Google to improve its products.
- **2026-11-17 Age limits:** the December 2025 Gemini API terms require users to be 18+. Anthropic's consumer terms require 18+; the API falls under the Commercial Terms, whose age rule was not confirmed. Check the age rule before creating an API account.
- **2026-11-17 Android developer verification:** Google is enforcing developer verification for sideloaded apps on certified devices, first in Brazil, Indonesia, Singapore and Thailand from 2026-09-30, globally planned for 2027. A free limited-distribution account (students, hobbyists) allows up to 20 devices. Affects "anyone can install" from 2027 on; needs a decision before the first public release.
- **2026-11-17 iOS:** Siri cannot be replaced by third-party apps, and a free Apple account means re-signing the app every 7 days. Out of scope for now.
