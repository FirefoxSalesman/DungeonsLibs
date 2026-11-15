# DungeonsLibs — agent notes

Minecraft 1.20.1 Forge mod (forge 47.3.0), package `net.firefoxsalesman.dungeonslibs`, mod id `dungeonslibs`. Java 17 toolchain (builds run on a Java 21 JVM). Gradle 8.8 wrapper.

## Commands

- `./gradlew --no-daemon compileJava` — fast compile check (use this to verify changes).
- `./gradlew --no-daemon build` — full build.
- Gradle daemon is disabled (`org.gradle.daemon=false`); always pass `--no-daemon`. Heap is `-Xmx3G`.
- No test suite and no CI workflows exist; `compileJava` is the primary verification step.

## Entry point / architecture

- `src/main/java/net/firefoxsalesman/dungeonslibs/DungeonsLibs.java` — `@Mod` entry point.
- `DungeonsLibs.REGISTRAR` = `FragmentumRegistry.INSTANCE.registrar(MOD_ID)` (Fragmentum registry).
- Registrations use `REGISTRAR.register(Registry|ResourceKey, ResourceLocation, () -> instance)`; the lambda is a Kotlin `Function0`.
- `Deferred<T>` holders are read with `.get()`.

## Critical gotcha — Kotlin stdlib (do not remove)

Fragmentum (`dev.obscuria.fragmentum`) is a Kotlin library whose Java API exposes `kotlin.jvm.functions.Function0` and `kotlin.Lazy`. `build.gradle` must declare:

```groovy
implementation 'org.jetbrains.kotlin:kotlin-stdlib:1.9.23'
```

It is placed right after the Fragmentum dependency. Without it, `compileJava` fails with:
- `class file for kotlin.jvm.functions.Function0 not found`
- `class file for kotlin.Lazy not found`
- `Function0 is not a functional interface` (cascades into all registry-call overload errors)

## Mixins

MixinGradle; config `mixins.dungeonslibs.json`, refmap `mixins.dungeonslibs.refmap.json`. Mixin warnings about unmapped target methods are normal under parchment mappings and are not build failures.

## Environment

- Nix dev shell via direnv (`.envrc` = `use flake`, `flake.nix` at root).
- `JAVA_TOOL_OPTIONS` includes a lombok javaagent; `_JAVA_OPTIONS` sets `java.util.prefs.userRoot` — both produce harmless "Picked up" lines in build output.
- Build outputs live in `build/`.

## Versions (gradle.properties)

minecraft 1.20.1, forge 47.3.0, geckolib 4.7, curios 5.10.0+1.20.1, mixinextras 0.3.5, mappings parchment 2023.06.26-1.20.1. Mod version 1.4.2.
