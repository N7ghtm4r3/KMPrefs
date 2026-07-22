---
name: route-test-designer
description: Design and implement backend route/API tests with a route-aware TDD flow. Use when Codex is asked to create, expand, review, or explain integration/API tests for server routes, endpoint coverage, authorization branches, role-based behavior, request validation, route-specific edge cases, bug fixes, new route behavior, or test-first/red-green-refactor route work.
---

# Route Test Designer + TDD

## Core Rule

Use one integrated route-testing flow:

- For existing route behavior or test-only work, derive tests from the route's actual behavior, not from a generic role matrix or a guessed API contract.
- For new route behavior or bug fixes, use test-driven development: write one failing route/API test for the desired public behavior, implement the smallest production change to pass, then repeat.

Tests should verify behavior through public interfaces, not implementation details. Prefer real HTTP flows through the API harness. A good route test reads like a specification: "PM cannot update another PM's client" or "refresh stores partial provider metrics and falls back for failures."

Be brutally exhaustive for the target route when the task is to add or expand route coverage. "Good coverage" means every meaningful way the route can return a different status, shape, data set, side effect, or security posture is either tested or explicitly called out as unreachable/defensive/residual risk. Do not describe a suite as exhaustive merely because coverage percentages are high.

Never modify production code only to make coverage easier or reach 100%. Do not refactor, simplify, remove defensive branches, export internals, change serializers, change auth behavior, or otherwise adapt production code for coverage unless the user explicitly asks for a production fix or new behavior. If 100% cannot be reached through tests alone, document the exact unreachable line/branch and why.

Before writing tests:

1. Read the target route handler.
2. Read the router mounting file to confirm the full URL path.
3. Read auth middleware and shared helpers used by the route.
4. Read schemas/tables/services touched by the route.
5. Inspect existing tests and reuse their harness, lifecycle, naming, and data setup patterns.
6. Decide the mode:
   - **Coverage/documentation mode**: test current behavior only; do not change production.
   - **TDD feature/fix mode**: write one failing test for desired behavior, then change production minimally to pass.

## Route-Aware TDD Flow

Use this flow when the user asks for TDD, a bug fix, new route behavior, or a changed route contract. Also use the same vertical rhythm when adding route coverage: one behavior slice at a time, with coverage checks after meaningful batches.

### 1. Route Reconnaissance

Read first, then test:

- target route handler
- router mounting file and full URL path
- auth middleware and user/session serialization
- schema/table/service definitions
- existing tests and harness conventions
- current behavior for surprising or unsafe branches

For test-only tasks, this reconnaissance defines what must be documented. For feature/fix tasks, it defines the current baseline and the smallest public behavior that should change.

### 2. Scenario Inventory

Build a scenario inventory before editing tests. Keep it behavior-oriented, not implementation-oriented:

- **Auth**: anonymous, authenticated allowed role, authenticated disallowed role, ownership/cross-owner cases.
- **Input**: required fields, type coercion, invalid IDs, invalid dates/ranges, unknown enum/filter/status values, empty or whitespace-only strings.
- **Data states**: no rows, one included row, one excluded row, mixed included/excluded rows, null optional values, duplicate/conflict rows.
- **Side effects**: inserted/updated/deleted rows, no-op behavior, idempotency, external calls, and absence of side effects on failures.
- **Response contract**: exact stable body, dynamic fields, ordering guarantees or lack of ordering, and absence/presence of sensitive fields.

In TDD feature/fix mode, mark which inventory items are desired new behavior and which are current behavior that must remain stable.

### 3. Vertical Red-Green Slices

Do not write all tests first and then all implementation. That horizontal slicing creates tests for imagined behavior and makes the suite brittle.

Use vertical slices:

```text
RED:   Write one route/API test for one public behavior -> prove it fails for the expected reason.
GREEN: Implement the smallest route/schema/service change needed -> prove the test passes.
CHECK: During development, a narrow command is allowed as a quick local signal. Before any final response, stop and ask the user which final validation gate to run, unless the user already chose the gate in the same turn.
NEXT:  Use what you learned to choose the next behavior.
```

Rules:

- One behavior at a time.
- One failing test at a time in TDD feature/fix mode.
- Do not anticipate future tests with speculative production code.
- Keep tests focused on observable HTTP behavior and durable side effects.
- Never refactor while red.
- When all selected behaviors are green, refactor only if it improves clarity without changing behavior, and rerun tests after each refactor step.

### 4. Tracer Bullet First

For new endpoints or large route changes, start with a tracer bullet: one end-to-end route test that proves the request can travel through auth, routing, validation, handler logic, persistence/external client if relevant, and response serialization.

Examples:

- `POST /api/reports` creates the minimal valid report for an authorized user.
- `PATCH /api/clients/:id` rejects a cross-owner manager before any database write.
- `GET /api/dashboard/kpis` returns zero values for an authenticated user with no data.

After the tracer bullet passes, add edge cases and branches one at a time.

### 5. Existing Behavior vs Desired Behavior

Be explicit about which behavior is being tested:

- **Current behavior documentation**: use when the user asked for tests/coverage only. If the route currently allows unsafe behavior, write a passing test that documents it honestly and report residual risk.
- **Desired behavior TDD**: use when the user asked for a bug fix, product change, security hardening, or new route behavior. Write a failing test for the desired invariant, then change production code to pass it.
- **Ambiguous behavior**: if changing production behavior would be risky and the user did not ask for it, document current behavior and call out the risk instead of silently changing the route.

Examples:

```ts
it("documents that managers can currently access unassigned reports", async () => {
  // current behavior documentation: passing test, residual risk in final report
});
```

```ts
it("prevents managers from accessing reports owned by another manager", async () => {
  // desired behavior TDD: failing test first, production fix second
});
```

### 6. Per-Cycle Checklist

Before moving to the next slice:

```text
[ ] Test describes public route behavior, not implementation.
[ ] Test uses HTTP/harness flow unless there is a strong reason not to.
[ ] Test fails for the expected reason before production changes in TDD mode.
[ ] Production code is minimal for the current behavior.
[ ] Important response fields and side effects are asserted.
[ ] No speculative branches or unrelated refactors were added.
```

## Scenario Selection

Cover each behavior branch that can change the HTTP result or response body.

Always consider:

- unauthenticated access
- authorized happy path
- authorization branches by role or ownership
- cross-role attempts to read or mutate someone else's data, even when the route currently allows it
- validation failures
- malformed-but-parseable inputs such as whitespace strings, invalid dates, unknown enum values, empty arrays, and negative or zero numbers
- empty-state responses
- not-found paths
- conflict paths
- forbidden paths
- boundary values for dates, ranges, pagination, filters, and statuses
- null/optional fields when the route treats them specially
- cross-tenant or cross-owner isolation
- side effects in the database or external storage when relevant
- external service success, partial success, non-OK responses, thrown errors, and request metadata such as URL, method, headers, and body
- sensitive data leakage when a route spreads database rows into responses

Do not test every possible role automatically. Test a role only when the route distinguishes it or when it represents a distinct authorization branch.

Example decision:

```ts
if (req.user.role === "admin") {
  // branch A
} else {
  // branch B
}
```

Test `admin` and one representative non-admin role, usually the role most natural for the scenario. Do not add redundant tests for every non-admin role unless the route or product requirement treats them differently.

Example decision:

```ts
if (["admin", "pm"].includes(req.user.role)) {
  // branch A
} else if (req.user.role === "cliente") {
  // branch B
} else {
  // branch C
}
```

Test `admin` or `pm` as appropriate, `cliente`, and one representative role for the final branch.

## Repository Pattern

Follow the current test harness pattern. Do not import runtime modules that require environment variables before the harness initializes them.

Preferred structure:

```ts
import { afterAll, beforeAll, beforeEach, describe, expect, it } from "vitest";

import {
  resetApiTestData,
  startApiTestHarness,
  stopApiTestHarness,
} from "../../infra/test-harness";

type DbModule = typeof import("@workspace/db");

let harness: Awaited<ReturnType<typeof startApiTestHarness>>;
let dbModule: DbModule;

beforeAll(async () => {
  harness = await startApiTestHarness();
  dbModule = await import("@workspace/db");
});

beforeEach(async () => {
  await resetApiTestData();
});

afterAll(async () => {
  await stopApiTestHarness();
});
```

Rules:

- Keep `type DbModule = typeof import("@workspace/db")` top-level if needed for typing.
- Use `await import("@workspace/db")` only after `startApiTestHarness()`.
- Prefer real HTTP flows through `harness.api` over direct handler calls.
- Prefer real login/session flows over fabricating auth state.
- Add route-specific reset helpers only for tables the tests create.
- Keep test data minimal but intentional.

## Test Data Design

Build fixtures that prove inclusion and exclusion.

For aggregation or list endpoints, create:

- data that should be included
- data that should be excluded by ownership, role, status, date, or filter
- null/optional values if the route has explicit null handling
- boundary values for inclusive/exclusive comparisons

Expected values should document route behavior exactly, even when the behavior is surprising. If the route counts all accessible rows for one KPI but only in-range rows for another KPI, make that distinction visible in the fixture names and assertions.

When the actual behavior appears unsafe or surprising, do one of these explicitly:

- If the user asked to preserve current behavior, write a test that documents it and name the test honestly, for example `documents that managers can currently access unassigned reports`.
- If the route violates an obvious security/product invariant, prefer a failing test only when the user asked to fix the route or when the expected invariant is already established elsewhere in the codebase.
- In the final report, call out any documented unsafe behavior as residual risk. Do not let a passing test suite make it sound safe.

For sensitive data, inspect the source table fields. If the route returns a spread object such as `{ ...user }` or `{ ...client }`, assert either:

- sensitive fields are absent, when the route intentionally serializes public data; or
- sensitive fields are present, when documenting the current behavior without changing the route, and report it as a security gap.

## Assertions

Assert more than status codes.

For each meaningful test, verify:

- status code
- response shape
- important response values
- absence of sensitive fields
- database side effects when the endpoint writes data
- isolation from inaccessible data when authorization filters apply

Use exact body assertions when the response is stable. Use partial assertions only when the route returns dynamic fields.

Do not rely on accidental database order unless the route has an explicit `orderBy`. If the route does not order rows, either sort the response in the assertion by a stable key or assert set membership with `expect.arrayContaining`.

Examples:

```ts
expect(body).toEqual({
  activeCount: 1,
  totalCount: 3,
});
```

```ts
expect(body.user.id).toEqual(expect.any(String));
expect(body.user).not.toHaveProperty("passwordHash");
```

## Coverage Interpretation

Coverage is a diagnostic, not the source of truth.

After running coverage:

- Ensure the target route file appears in the report.
- Check uncovered lines and branches against the route implementation.
- Add tests for every executable behavior gap.
- Do not stop at "good enough" or "meaningful enough" while the target route is below 100% statements, lines, functions, or branches.
- Avoid artificial tests that only exercise impossible states through direct handler calls unless the user explicitly asks for implementation-agnostic coverage tricks. Prefer real HTTP flows. Do not change production code to make a branch testable.

A route can be considered complete only when it reaches 100% statements, 100% lines, 100% functions, and 100% branches for the target route file, or when the remaining gap is proven unreachable/defensive and explicitly reported with the exact lines/branches and reason. If the route contains unreachable defensive code that prevents 100%, report that limitation. Do not recommend or perform production refactors as part of this skill unless the user separately asks to change production behavior.

Use this language precisely:

- **Exhaustive for current behavior**: all meaningful executable branches, data states, authorization attempts, side effects, and response risks have tests.
- **Well-covered**: major behavior branches are tested, with named residual gaps.
- **Partial**: only happy paths and a few failures are tested.

If a route has known security gaps that tests merely document, never call it "safe" or "fully hardened"; call it "exhaustively documented for current behavior" and list the gaps.

## Post-Integration Validation Choice

After adding or changing a route test suite, the final validation choice is mandatory. Do not automatically run the full route/integration suite with coverage. First run the current route test file or a similarly narrow command while iterating, then stop and ask the user which final validation scope they want:

- **Complete gate**: run the repository's full route/integration suite with coverage and validate the target route file in the coverage table. This is slower but gives the strongest signal and avoids claiming exhaustive coverage from a narrow run.
- **Current-suite gate**: run only the newly added or changed route test file, optionally with narrow coverage if useful. This is faster and appropriate when the user wants a quick feedback loop, but the final report must say that the complete route/integration coverage gate was intentionally skipped.

Mandatory stop rule:

- Ask this as a short question after implementation and narrow iteration are done, before starting the costly final command and before sending the final answer.
- Do not send a final answer for route-test implementation work until one of these is true:
  - the user has explicitly chosen **complete gate** or **current-suite gate** in the same turn or in response to the stop question;
  - the user explicitly asked for no test execution;
  - the validation command is technically impossible to run, and the blocker plus exact command are reported.
- A successful narrow/iterative test run is not a final validation choice. It is only a development signal.
- If you accidentally run a narrow test during implementation, you must still ask the final validation question instead of treating that run as the selected current-suite gate.
- If the user already explicitly requested either full validation or quick/current validation in the same turn, follow that choice without asking again and state that this was the chosen final gate.

Use this exact final validation question unless there is a strong reason to adapt wording:

```text
Implementazione pronta. Quale gate finale vuoi che esegua?
1. Complete gate: full route/integration suite con coverage.
2. Current-suite gate: solo questa suite/route test, piu veloce.
```

Do not run the final complete coverage gate against only the newly added or changed test file. Targeted coverage runs can overwrite or reset the shared coverage dashboard and make unrelated route rows look like zero coverage. If the user chooses the current-suite gate, report it as targeted validation rather than complete route coverage.

Expected workflow:

1. Run the current route test file while iterating.
2. Fix failures and review the scenario inventory against the route.
3. Mandatory stop: ask the user to choose **complete gate** or **current-suite gate**, unless they already specified the scope or explicitly asked for no tests.
4. If they choose complete gate, run the repository's full route/integration coverage command with no target-file filter.
5. Locate the target route file in the coverage table.
6. Check statements, lines, functions, and branches.
7. If the target route is below 100% for statements, lines, or functions, inspect the uncovered lines and add missing meaningful tests.
8. If branch coverage is below 100%, inspect each uncovered branch and either add a meaningful test or explicitly report why that branch is unreachable, defensive, or redundant.
9. Repeat coverage rounds until the target route row is 100% statements, 100% branches, 100% functions, and 100% lines, or until the user chooses to stop at current-suite validation.
10. If any metric remains below 100%, inspect the exact uncovered lines/branches and do one of the following:
   - add another real HTTP/integration test that covers it;
   - if it is unreachable because current code throws or returns earlier, report it as unreachable with the exact upstream reason;
   - if it is dead defensive code, report it as dead defensive code and leave production code unchanged.
11. Do not call the route exhaustive when it remains below 100% unless the final report explicitly says "exhaustive except for unreachable/defensive lines X-Y" and explains why those lines cannot be reached through the public route.

When reporting a complete gate result, include the target route's coverage row, not only the global `All files` row. When reporting a current-suite gate result, include the exact targeted command, say that full route/integration coverage was skipped by user choice, and include a compact result table for the single test file that was run. Use the same coverage-table shape whenever coverage is available: `File | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s`. If the selected current-suite gate did not collect coverage, still include that table shape with `N/A` values and add a short line with the targeted test result count and duration. This makes the targeted gate auditable instead of relying on a vague "green" summary.

Example report:

```text
dashboard.ts | 100% statements | 94.73% branches | 100% funcs | 100% lines
Remaining branch: defensive fallback on engagementRate ?? 0, unreachable after engagementRate !== null filter.
```

## Validation

Run the repository's existing test command whenever possible. If the command requires Docker, Testcontainers, local network, or database access, request the required approval rather than weakening the test.

For route test work, final validation must ask the user to choose between the complete route/integration coverage command and the current route test suite. Do not present a current-suite run as complete validation; state the chosen scope clearly. If the complete command is unavailable, state the blocker and leave the exact full command for the user.

Before final response checklist:

```text
[ ] I ran any narrow test commands needed for iteration.
[ ] I asked the mandatory final validation question, unless the user already chose a gate or explicitly asked for no tests.
[ ] I ran the chosen final gate, or reported why it could not run.
[ ] I did not describe a narrow iterative run as complete validation.
```

Report:

- command run
- pass/fail result
- number of tests/files
- route-specific coverage if relevant
- whether the target route reached 100% meaningful coverage
- any remaining meaningful gaps
- a documentation-style list of the tests that were integrated and what each one proves

If tests cannot be run, state why and provide the exact command the user should run.

## Final Test Documentation

After integrating or changing route tests, explain the new/changed tests as documentation, not as a vague summary.

This final documentation is mandatory for every route-test task. Do not compress it into a one-line summary even when the implementation is small. The final response may stay concise overall, but it must still include an endpoint-by-endpoint test inventory that names the behavior, setup, expected status, and assertion/side effect for each meaningful test or grouped validation slice.

Group the explanation by endpoint or behavior area. For each test, include:

- the test name or a concise paraphrase
- the route/method it exercises
- the setup that matters, such as auth role, ownership, fixture rows, external mock, or malformed input
- the expected HTTP status
- the specific response fields, side effects, isolation rule, external request, or security behavior it proves

Prefer this shape:

```text
POST /api/chat
- rejects unauthenticated chat requests: sends a valid-looking message without cookies and proves the route returns 401 without reaching the AI client.
- streams AI deltas: mocks an SSE upstream with valid chunks, malformed JSON, empty deltas, and [DONE]; proves only real content deltas and the final done event are emitted.
```

When tests document unsafe current behavior, say that plainly:

```text
GET /api/analytics/:clientId/report
- documents current cross-owner access: logs in as a manager and requests another manager's client report; proves the route currently returns 200. Residual risk: this is not an authorization guarantee.
```

Do not just say "added tests for auth, validation, and happy path." Name what was actually tested and why it matters.
