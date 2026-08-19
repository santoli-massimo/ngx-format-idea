# Ngx Format

> **Status: experimental.** Built in early 2025 as an exploration of IntelliJ
> Platform language internals. It works, but it is not finished — see
> [Status](#status). Not published on the JetBrains Marketplace.

An IntelliJ Platform plugin that prototypes a **single-file component format
for Angular**: template, style and component class in one `.ngx` file, the way
Vue and Svelte do it.

```
[template]
<div>
  <p>hello works!</p>
  <button>{{ counter }}</button>
</div>
[/template]

[style]
div {
    max-width: 1200px;
    margin: 0 auto;
}
[/style]

[component]
import { Component } from '@angular/core';

@Component({ selector: 'app-hello', standalone: true })
export class HelloComponent {
  public counter = 0;
}
[/component]
```

## Why

Angular splits every component across three files (`.html`, `.scss`, `.ts`).
That's a lot of file-hopping for what is conceptually one unit. This plugin
explores what a single-file-component developer experience could look like in
IntelliJ — without changing the Angular build: the `.ngx` file stays in sync
with the standard component files.

## What works

- **Custom language**: JFlex lexer + Grammar-Kit BNF parser + PSI tree with
  mixins for each block type
- **Syntax highlighting** for the block markers
- **Multi-host language injection**: the content of `[template]`, `[style]`
  and `[component]` blocks is injected as real HTML, CSS and TypeScript —
  with the full IDE support of each language inside its block
- **Project-tree integration** (`TreeStructureProvider`): the `.ngx` file
  groups its component files in the project view
- **Live sync**: edits to the `.ngx` file propagate to the underlying
  component files in real time

## Status

Working prototype. The next milestone — code completion across injected
blocks — is where development stopped. Known rough edges: no tests, no
formatter, the grammar covers the three core blocks only.

## Development

Requires IntelliJ IDEA **Ultimate** (the plugin depends on the bundled
JavaScript/Angular plugins).

```bash
./gradlew runIde     # launches a sandbox IDE with the plugin
./gradlew buildPlugin
```

The lexer and parser sources are generated from `NgxLexer.flex` and
`NgxParser.bnf` by the Grammar-Kit Gradle tasks (`generateLexer`,
`generateParser`), which run automatically before compilation.

## License

[MIT](LICENSE)
