---
name: audit-cicd-security
description: Audit repository CI/CD pipelines across GitHub Actions, GitLab CI, Jenkins, Azure Pipelines, CircleCI, Bitbucket Pipelines, Buildkite, Tekton, Argo Workflows, and equivalent systems. Use when asked to review pipeline security, trust boundaries, fork or merge-request exposure, cache poisoning or disclosure, artifact provenance, credential leakage, privileged triggers, third-party components, runner isolation, deployment or publishing gates, or to remediate confirmed Critical and High pipeline findings after explicit approval.
---

# Audit CI/CD Security

Perform a repository-evidence-based security audit. Remain read-only unless the user explicitly approves the remediation phase after seeing the completed audit.

## Enforce the operating mode

1. Start in read-only audit mode.
2. Do not edit repository files, trigger pipelines, execute untrusted pipeline code, publish artifacts or packages, deploy, rotate or retrieve secrets, or change CI/CD platform, repository, organization, runner, environment, or cloud settings.
3. Complete and present the audit before proposing any write.
4. End the audit with a clear approval gate. Ask whether to implement the confirmed Critical and High remediations.
5. Treat silence, an ambiguous reply, prior general permission to inspect the repository, or a request to “audit” as no approval.
6. Proceed only after an explicit affirmative response authorizing remediation.
7. After approval, modify only repository files needed for confirmed Critical and High findings reported in the audit. Do not fix Medium or Low findings unless the user separately and explicitly expands scope.
8. Never infer approval for deployments, publishing, secret rotation, remote pipeline execution, or external settings changes. Require separate explicit approval for each such action.

## Discover the pipeline surface

Identify the CI/CD system from repository evidence. Inspect all relevant configuration, including:

- GitHub Actions workflows and local actions;
- `.gitlab-ci.yml` and included GitLab CI configuration;
- `Jenkinsfile` and shared-library references available in the repository;
- Azure Pipelines YAML and templates;
- CircleCI, Bitbucket Pipelines, Buildkite, Drone, Woodpecker, Tekton, Argo, and equivalent configuration;
- reusable workflows, templates, local actions, plugins, orbs, tasks, and pipeline components;
- every invoked script, Makefile target, package script, container or build configuration, and infrastructure/deployment entry point available in the repository.

Follow local references transitively. Record remote includes or components that cannot be inspected as unknown; do not assume they are safe. Do not execute code controlled by an untrusted contributor.

## Apply the threat model

Assume an external attacker can create a fork or branch where supported, control proposed-change contents and metadata, choose filenames and refs, open a pull or merge request, and influence any low-trust event inputs.

Evaluate whether the attacker can:

- read trusted caches or recover credentials stored in cache, artifact, workspace, log, image layer, or build output;
- write or poison content later consumed by a trusted or privileged pipeline;
- cause privileged triggers, chained pipelines, comments, dispatches, API events, or scheduled jobs to execute attacker-controlled code;
- exfiltrate platform tokens, personal tokens, package registry credentials, cloud credentials, signing keys, OIDC tokens, deployment credentials, or runner-resident secrets;
- poison dependencies, tool caches, package-manager caches, build outputs, Docker layers, artifacts, reports, or generated configuration;
- exploit overprivileged tokens, unpinned third-party components, mutable remote includes, unsafe interpolation, command injection, or weak environment gates;
- reach persistent or self-hosted runners from untrusted jobs.

Treat caches and artifacts as data transfer mechanisms, not security boundaries. Treat every object that crosses from lower to higher trust as untrusted until its provenance and integrity are verified.

## Audit systematically

### Inventory trust boundaries

For every pipeline and job:

- identify triggers and classify them as trusted, untrusted, privileged, or ambiguous;
- identify the checked-out or downloaded revision and who controls it;
- identify runner type, persistence, network reach, environment, approvals, and concurrency where visible;
- determine token permissions and secret availability;
- trace upstream and downstream pipelines, artifacts, caches, manual gates, and deployments.

Use platform-neutral reasoning, then name the platform-specific behavior supporting the conclusion. If behavior depends on remote settings or current service semantics that cannot be established from available evidence, label it unknown.

### Inspect caches and artifacts

For every explicit or implicit cache and every uploaded or downloaded artifact, report:

- paths, namespace, key, fallback or restore keys, retention, and scope;
- which trust levels can read and write it;
- attacker control over its key, paths, contents, metadata, or restored executable files;
- whether trusted jobs consume content written by untrusted jobs;
- whether it can contain an entire home directory or workspace, VCS metadata, credential files, cloud or container authentication, environment dumps, temporary credentials, generated executables, or deployment material;
- whether integrity, provenance, immutability, and expected producer identity are verified before privileged use.

Include setup-tool caches, package-manager caches, Docker or BuildKit caches, compiler/build caches, remote cache services, workspace persistence, reports, and custom implementations.

### Trace credentials

For each credential or capability, using names only:

- identify where it becomes available and the minimum job or step that can access it;
- determine whether untrusted code, input interpolation, hooks, plugins, or generated scripts can reach it;
- identify files, environment variables, logs, process arguments, credential helpers, workspaces, caches, artifacts, or image layers it may enter;
- verify cleanup order relative to cache or artifact upload;
- determine lifetime, scope, revocation, OIDC audience/subject constraints, and environment approval where observable;
- check whether source checkout or VCS helpers persist credentials.

Never print, decode, retrieve, or test real secret values.

### Check privileged execution

Pay special attention to:

- privileged pull/merge-request variants that fetch or execute contributor-controlled revisions;
- parent/child, completion, downstream, or chained pipelines that trust artifacts, refs, caches, variables, or scripts from lower-trust runs;
- comment, issue, chatops, dispatch, webhook, API, and agentic pipelines processing attacker-controlled text while holding credentials;
- deployment and publishing jobs lacking protected environments, approvals, trusted refs, or separation from build jobs;
- third-party actions, plugins, images, or reusable components not pinned to immutable verified digests or full commit identifiers;
- untrusted workloads on persistent or self-hosted runners.

Do not treat checkout defaults, masking, read-only tokens, protected variables, or cache isolation as sufficient without verifying the complete data and execution path.

## Report evidence, not generic advice

Use this structure:

### Executive verdict

Return `SAFE AGAINST THIS THREAT MODEL`, `CONDITIONALLY SAFE`, or `UNSAFE`. Explain in no more than five sentences.

### Pipeline and trust inventory

List each pipeline/job, trigger, trust level, runner, revision source, token permissions, secrets, and privileged capabilities.

### Findings

For every finding include:

- severity: Critical, High, Medium, or Low;
- exact file and line;
- platform, trigger, and trust boundary;
- cache, artifact, workspace, or storage path involved;
- credential or capability at risk;
- concrete attacker-controlled input;
- step-by-step exploit path;
- why existing protections do or do not stop it;
- minimal remediation;
- evidence status: confirmed or unknown.

Do not report a generic best practice without connecting it to repository evidence. Do not classify an unknown as a confirmed vulnerability.

### Cache and artifact access matrix

| Pipeline/job | Trigger | Trust level | Object/key | Read access | Write access | Executable content | Credential exposure | Integrity/provenance | Safe? |
|---|---|---|---|---|---|---|---|---|---|

### Credential-to-storage trace

| Credential/capability | Introduced by | Available to | Written path | Cached/uploaded? | Lifetime | Risk |
|---|---|---|---|---|---|---|

Use credential names only, never values.

### Required patches

Provide minimal proposed YAML or script patches for confirmed findings while preserving behavior where possible. These are proposals only during read-only mode; do not apply them.

### Validation plan

Describe validation without production secrets. Include canary credentials, low-trust inability to read or update trusted state, rejection or verification of low-trust artifacts, non-execution of poisoned cache content, and unavailability of deployment credentials before approval. List external settings that require manual verification.

### Approval gate

State that no files have been changed. Ask explicitly: “Vuoi che implementi ora esclusivamente le remediation confermate Critical e High?”

## Remediate only after approval

After explicit approval:

1. Reconfirm the approved findings and exact local files.
2. Apply minimal changes only for confirmed Critical and High findings.
3. Add concise comments where useful to explain the trust boundary and why the guard exists.
4. Preserve intended pipeline behavior unless security requires otherwise; report any unavoidable behavior change.
5. Run syntax and static validation for every modified workflow, template, script, or configuration using non-executing validators. Do not run untrusted build or deployment steps.
6. Inspect the final diff for secret material, unrelated changes, syntax errors, and scope creep.
7. Present validation results and the complete final diff.
8. Leave remote execution, deployments, publishing, secret operations, and external settings unchanged unless separately and explicitly approved.
