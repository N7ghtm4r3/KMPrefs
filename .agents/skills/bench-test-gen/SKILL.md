---
name: bench-test-gen
description: Generate concrete, language-agnostic, repository-native behavioral test files for the main features of a software library, and generate performance benchmark sources only when the user explicitly requests benchmark implementation. Use when Codex must inspect a library and actually create or expand compile-ready tests for executable behavior and branch coverage, generate regression test files, or—after explicit authorization—create separate latency, throughput, and allocation benchmark files without modifying production code or executing generated artifacts.
---

# Bench Test Gen

## Core Contract

Inspect the repository in read-only mode, identify the library's principal consumer-facing features, and generate only test-related source files.

Allowed writes:

- unit, integration, compatibility, property-based, and regression test sources;
- performance benchmark sources;
- fixtures, fakes, test data, and helpers stored inside existing test or benchmark areas;
- new test or benchmark directories when their location follows repository conventions.

Forbidden writes:

- production sources;
- public or internal library implementation;
- build files, dependency manifests, lockfiles, compiler configuration, CI workflows, or coverage configuration;
- documentation, examples, generated production assets, or unrelated files;
- existing tests outside the requested scope unless the user explicitly asks to expand them.

Never run tests, benchmarks, coverage, builds, compilers, linters, formatters, profilers, or generated code. Never install dependencies. Generate the requested test artifacts and leave execution to the user.

If correct test generation would require a production change, new dependency, build configuration, or unsupported harness, do not make that change. Generate every valid in-scope artifact possible and report the exact missing prerequisite.

## Mandatory Generated Deliverable

Always create or update concrete test source files. Analysis is preparation, not the deliverable.

A successful use of this skill must produce:

- at least one real coverage test file when coverage generation is requested;
- at least one real benchmark source file when benchmark implementation is explicitly requested;
- both kinds of files when both generations are explicitly requested.

Do not stop after:

- identifying principal features;
- listing scenarios or branches;
- proposing filenames;
- showing pseudocode or illustrative snippets;
- recommending a framework;
- explaining what tests should exist.

Write complete repository-native source code with the correct language, file extension, package/module declaration, imports, framework annotations or registration, fixtures, setup, assertions, and benchmark methods. Do not leave `TODO`, placeholder bodies, ellipses, pseudocode, or examples that still need to be converted into files.

Do not claim completion until the files exist in the repository. If no valid test or benchmark file can be generated with the repository's existing dependencies and configuration, mark the generation as blocked and name the missing prerequisite. Never substitute a test plan for the required source files.

## Keep Coverage and Performance Separate

Treat the two outputs as independent suites.

- **Coverage suite**: generate tests that exercise the public behaviors, meaningful inputs, failures, state transitions, side effects, and consumer-reachable executable branches of the principal features.
- **Performance suite**: generate benchmark code that can measure latency, throughput, and allocations reliably when the user runs it.

Never present coverage as a performance signal. Never present benchmark design as behavioral coverage. Keep files, fixtures, setup, commands, tables, and conclusions distinct even when both suites target the same feature.

Because this skill never executes generated code:

- do not claim that tests pass or fail;
- do not claim actual coverage percentages;
- do not report measured performance values;
- describe expected coverage and intended benchmark metrics only;
- provide optional commands for the user to run without running them.

## Require Explicit Benchmark Implementation

Do not create or modify benchmark files unless the user explicitly asks to implement, create, add, or generate benchmarks.

Treat requests such as these as explicit authorization:

- “implementa i benchmark”;
- “genera la suite benchmark”;
- “aggiungi benchmark per queste feature”;
- “crea i file per misurare latenza e throughput”.

Do not treat these as implementation authorization:

- “analizza le performance”;
- “quali benchmark servirebbero?”;
- “individua le operazioni costose”;
- “come misureresti questa libreria?”;
- a generic request to generate tests that does not explicitly mention benchmark implementation.

Without explicit authorization, perform only read-only benchmark analysis when requested: identify candidate features, input sizes, metrics, harness support, and missing prerequisites. Do not write benchmark sources and state that benchmark implementation was not requested.

When the request is ambiguous, do not infer permission from the skill name or from a mention of performance. Keep benchmark files unchanged and ask whether the user wants to proceed with their implementation.

Once benchmark implementation is explicitly authorized, generate complete repository-native benchmark source files. The authorization covers only benchmark files and benchmark-local fixtures/helpers; it does not authorize production, dependency, build, lockfile, coverage, or CI changes.

## 1. Detect the Ecosystem Without Assuming a Language

Infer the repository's language and conventions from its files. Do not prefer a language, framework, or build system in advance.

Read:

1. source layout and package/module exports;
2. build and dependency files without modifying them;
3. existing test and benchmark directories;
4. existing test imports, lifecycle hooks, assertions, fixtures, and naming;
5. documentation and examples that describe supported consumer workflows;
6. public declarations and their implementations;
7. platform-specific source sets, targets, runtimes, and compatibility layers;
8. coverage and benchmark configuration when already present.

Select the existing framework and style whenever the repository establishes one. Examples may include native language test frameworks, third-party unit frameworks, property-based tools, or benchmark harnesses, but never assume any specific one.

If multiple ecosystems coexist:

- generate tests in the source set that owns the behavior;
- use shared/common tests for genuinely shared behavior;
- add platform-specific tests only for platform-specific contracts;
- avoid duplicating identical scenarios across targets without a compatibility reason.

If no test framework is established, do not add dependencies or configuration. Generate concrete test files only when the language provides a usable built-in facility. Otherwise mark generation as blocked and report the missing harness; do not present an inventory or pseudocode as completed test generation.

## 2. Identify the Principal Features

Build a feature inventory before writing files. Prioritize features that:

- appear in the public API, README, examples, or package exports;
- represent the main consumer workflows;
- create, read, update, transform, persist, parse, encode, decode, or validate data;
- control lifecycle, caching, synchronization, resource ownership, or configuration;
- have important failure, compatibility, concurrency, or performance behavior;
- are widely called or central to the library's purpose.

Deprioritize:

- trivial getters and setters without distinct behavior;
- generated code and simple data holders;
- private helpers that cannot be reached through a supported public interface;
- deprecated shims unless compatibility is part of the request;
- exhaustive testing of every declaration when a smaller feature-oriented suite captures the public contract.

For each selected feature, record:

- public entry points;
- documented or implementation-derived behavior;
- inputs, outputs, errors, state, and side effects;
- dependencies and nondeterministic boundaries;
- platform or runtime variants;
- existing test gaps;
- whether coverage tests should be generated;
- whether benchmarks would be useful and, separately, whether their implementation was explicitly authorized.

## 3. Derive Coverage Scenarios

Derive scenarios from actual behavior and consumer contracts, not a generic matrix.

Consider:

### Normal Behavior

- minimal valid and representative inputs;
- all materially different public result shapes;
- interaction between the feature's main methods;
- complete consumer workflows rather than isolated private steps.

### Boundaries and Invalid Input

- empty, null, missing, default, malformed, and unsupported values when the API admits them;
- zero, negative, maximum, overflow, precision, size, encoding, locale, and time boundaries;
- collection ordering, duplicates, equality, and identity behavior;
- documented error values or exception types.

### State and Side Effects

- fresh, populated, cleared, closed, disposed, and invalid lifecycle states;
- repeated calls, idempotency, caching, invalidation, and instance isolation;
- persistence and serialization round trips;
- emitted, stored, written, or externally visible effects;
- absence of mutation or external calls after failure;
- cleanup, rollback, and partial failure behavior.

### Compatibility and Concurrency

- supported platforms, runtimes, versions, encodings, and overloads;
- cancellation, reentrancy, thread-safety, and simultaneous calls only when relevant to the public contract;
- equivalent results across common and platform-specific implementations.

Classify each scenario as:

- current behavior to document;
- desired regression behavior expected to fail until production is fixed;
- consumer-reachable branch;
- unreachable or defensive branch;
- out of scope with a reason.

Generate regression tests for desired behavior when the user asks for a bug fix or new contract, but never implement the production fix.

## 4. Generate Coverage Tests

Create or expand actual test source files through public library interfaces. Avoid private access, reflection, exported test hooks, or internal call assertions unless the repository already treats them as supported test boundaries.

Follow existing conventions for:

- file placement and naming;
- imports and package/module declarations;
- setup, teardown, and resource lifecycle;
- assertion style;
- synchronous, asynchronous, and concurrent tests;
- fixtures, fakes, mocks, and temporary resources.

Prefer real local collaborators when deterministic and inexpensive. Use existing fakes or controlled test doubles at filesystem, network, database, clock, randomness, and other nondeterministic boundaries.

Make every test readable as a feature specification. Assert as applicable:

- return value and public result structure;
- documented error or exception;
- state before and after the call;
- durable side effect or its absence;
- ordering only when guaranteed;
- equality and round-trip fidelity;
- cancellation and cleanup;
- instance, thread, platform, or storage isolation.

Avoid:

- tautological expected values copied from the implementation algorithm;
- arbitrary sleeps;
- accidental dependence on iteration order;
- oversized fixtures that obscure the behavior;
- tests created only to touch a line without proving a consumer-visible outcome;
- snapshots for stable scalar behavior better expressed with explicit assertions.

Use property-based tests only when the repository already supports them or the language has an established built-in equivalent. Do not add a property-testing dependency.

## 5. Generate Performance Benchmarks

Enter this generation step only after explicit benchmark implementation authorization. Otherwise stop after read-only benchmark analysis and leave all benchmark files unchanged.

After authorization, create actual benchmark source files separately from coverage tests. Use the repository's existing benchmark framework and directory layout.

Design benchmarks for:

- **Latency**: time per operation with warmup and repeated samples;
- **Throughput**: operations, items, or bytes processed per unit of time with an explicit work unit;
- **Allocations**: bytes/op, objects/op, allocation count, allocation rate, or GC pressure when the existing platform harness supports it.

For every benchmark:

1. Reuse a correctness-tested public operation.
2. Keep setup outside the measured section unless setup is intentionally part of the workflow.
3. Include representative and boundary-relevant input sizes.
4. Prevent dead-code elimination using the framework's supported mechanism.
5. Include warmup for JIT runtimes when the framework supports it.
6. Configure multiple iterations or samples according to existing conventions.
7. Avoid I/O, clocks, randomness, logging, and unrelated setup inside microbenchmarks unless they are the target.
8. Keep environment-sensitive thresholds out of ordinary tests.
9. Prefer baseline comparison hooks already supported by the repository.

Do not invent an allocation metric the harness cannot collect. Generate latency and throughput benchmarks when valid, mark allocation measurement as unsupported, and report the missing capability instead of adding tools or dependencies.

Never place performance assertions in the coverage suite. Never generate fixed nanosecond or throughput thresholds without an existing calibrated baseline or explicit repository policy.

## 6. Preserve the Repository

Before writing, inspect the working tree and existing files. Preserve user changes and avoid overwriting unrelated work.

When creating artifacts:

- write only inside test or benchmark locations;
- reuse existing helpers before adding new test-local helpers;
- keep fixtures minimal and scoped;
- avoid changing existing snapshots through execution;
- do not create production-facing abstractions for test convenience;
- do not change visibility or expose internals;
- do not modify a method body even when a generated regression test is expected to fail.

If the requested scope cannot be completed without a forbidden write, stop at the boundary and report it.

## 7. Review Generated Artifacts Without Executing Them

After generation, perform a read-only review:

- confirm that every authorized generation track produced at least one concrete source file;
- confirm every changed file is a test, benchmark, or test-local fixture/helper;
- confirm imports and APIs follow nearby repository examples;
- confirm no generated file contains pseudocode, placeholders, `TODO`, or incomplete bodies;
- confirm no production, configuration, dependency, or lock file changed;
- compare the generated scenario inventory with the principal features;
- identify expected failing regression tests;
- identify code that could not be covered through public interfaces;
- identify benchmark metrics unsupported by the current harness.

Do not invoke a compiler, test discovery, dry run, linter, formatter, coverage collector, benchmark runner, or profiler as a substitute for this review.

## 8. Report Generation Results

Report only what was generated and what it is designed to prove.

Include:

- exact paths of generated or expanded test files;
- principal features covered;
- test inventory grouped by feature and public entry point;
- inputs, expected outputs/errors, state, and side effects asserted;
- expected failing regression tests;
- unreachable, defensive, or omitted behavior with reasons;
- benchmark files and intended latency, throughput, and allocation metrics;
- prerequisites the repository lacks;
- optional exact commands the user can run.
- whether benchmark implementation was explicitly authorized.

Use separate summaries:

```text
Coverage artifacts
Feature | Test file | Generated scenarios | Expected branch/behavior coverage | Known gaps

Performance artifacts
Feature | Benchmark file | Input sizes | Latency | Throughput | Allocations | Missing support
```

Use `planned` or `generated` for coverage status, never an unmeasured percentage. Use `not executed` for benchmark results, never fabricated timings or allocation values.

Use `generated` only when the corresponding source file was actually written. Never use `planned` as the final status for a successful request; reserve it for explicitly blocked scenarios that could not be materialized.

When benchmark implementation was not explicitly requested, report:

```text
Benchmark implementation requested: no
Benchmark files modified: no
```

State explicitly:

```text
Production code modified: no
Tests executed: no
Benchmarks executed: no
```

Do not claim that the suite is green, that a coverage target was reached, or that performance improved until the user executes the appropriate commands outside this skill.
