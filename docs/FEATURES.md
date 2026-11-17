# Features

The wishlist. Anything the maintainer wants "someday" lands here immediately, even if vague. Being on this list is not a promise for a release; the order lives in `ROADMAP.md`.

`[ ]` wanted, `[x]` done. Only confirmed wishes go here, not Claude's suggestions.

## Confirmed requirements

- [ ] Works as a replacement for Google Assistant
- [ ] Not tailored to one person: anyone reasonably technical can install and set it up
- [ ] Android app that is reliable enough for daily use, before anything else
- [ ] Windows app, later
- [ ] Easy to install on a new phone without an app store
- [ ] Distributed through versioned GitHub Releases

## Quality goals

Taken from what annoys the maintainer about Google Assistant.

- [ ] Never silently drops a request: when the user stops talking, it reacts, or says it did not understand
- [ ] Short, direct answers: "weather tomorrow" gets the forecast, not a speech

## Core

- [ ] Voice-first on Android, started by long-press (default digital assistant)
- [ ] German first; all texts and the voice language switchable so English can be added
- [ ] Claude as the AI, with the user's own API key
- [ ] Model picker in the settings (Haiku, Sonnet, Opus, Fable...), list loaded live so new models appear without an app update
- [ ] Gemini free tier as fallback when Claude fails or is out of credits
- [ ] "Open a new Claude chat": hands over to the official Claude app
- [ ] Memory starts with a one-time import (Claude memory export, own notes)
- [ ] Profile with basic info (name, birth date and so on), editable any time. Age is calculated from the birth date, never stored
- [ ] Setup on first start: asks for the profile fields and the needed permissions (location, notifications, microphone)
- [ ] Context awareness: every request knows where the user is, the weather there and the time, so suggestions fit ("it's raining and you're out in the countryside, so no golf")
- [ ] Writing style as its own memory section: learned over time from sent replies, viewable and editable, can be filled with examples at the start
- [ ] Settings and memory exportable, for moving to a new phone
- [ ] Time, date, timers, alarms
- [ ] Weather
- [ ] Calendar: read and create events (Google Calendar)
- [ ] Open apps
- [ ] Read incoming messages and notifications
- [ ] Reply to messages (WhatsApp, SMS, Discord, Instagram, any app with a reply button in its notification): Wisp understands what the user wants to say and writes it in the user's style, reads it back, sends after "yes"
- [ ] Per-app notification permission: the user picks which apps' notifications may go to the AI. For a blocked app the AI only gets "the user has disabled access for this app"
- [ ] Memory: remembers preferences across conversations ("make all lights pink" uses the same pink as last time)

## Later

- [ ] Windows app: mainly text, configurable global hotkey (after the Android app is daily-driver ready)
- [ ] Control the PC by voice from the phone or on the PC
- [ ] "Open a new Claude Code session on my PC" and "open a new Claude chat" on whichever device is in use
- [ ] Sync memory and settings between phone and PC, paired once with a code
- [ ] Smart home control (lights) through Home Assistant, once it runs on the homelab
- [ ] Wake word ("Hey Wisp")
- [ ] English
- [ ] Choose the voice of Wisp

## Ideas, not decided yet

- [ ] Other AI providers (ChatGPT and so on), only if someone actually needs them
