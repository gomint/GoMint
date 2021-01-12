# gomint

<!-- BUILD BADGE -->
<a href="https://github.com/GoMint/GoMint/actions?query=workflow%3A%22Java+CI%22">
  <img alt="Build Status" src="https://github.com/GoMint/GoMint/workflows/Java%20CI/badge.svg">
</a>

<!-- STAR BADGE -->
<a href="https://github.com/GoMint/GoMint/stargazers">
  <img alt="GitHub Stars" src="https://img.shields.io/github/stars/GoMint/GoMint.svg">
</a>

<!-- ISSUES BADGE -->
<a href="https://github.com/GoMint/GoMint/issues">
  <img alt="GitHub Stars" src="https://img.shields.io/github/issues/GoMint/GoMint.svg">
</a>

<!-- VERSION BADGE -->
<a href="https://github.com/GoMint/GoMint">
  <img alt="GitHub Stars" src="https://img.shields.io/badge/version-1.0.5-green.svg">
</a>

<!-- LICENSE BADGE -->
<a href="https://opensource.org/licenses/BSD-3-Clause">
  <img alt="License" src="https://img.shields.io/badge/License-BSD%203--Clause-blue.svg">
</a>

GoMint is a modern Minecraft Bedrock Edition server enabling you to let your visions become true.

#### Features:
- 🚄 High performance, fast startup and low resource usage
- 🔧 Highly configurable via configuration files
- 🔌 Functionality extendable via plugins
- 🗝 Cryptography implementations in Rust
- 📦 Vanilla says "Hello": Crafting, enchanting, all blocks, all items

## ✳ Getting Started
**You can download the latest build from [here](https://gomint-artifacts.s3.amazonaws.com/latest.zip).**

### Windows Command Line
```bash
powershell.exe "$ProgressPreference = 'SilentlyContinue'; $Random = Get-Random -Maximum 0xFFFFFF; Invoke-WebRequest -Uri 'https://gomint-artifacts.s3.amazonaws.com/latest.zip' -OutFile gomint-$Random.zip; Expand-Archive -LiteralPath gomint-$Random.zip -DestinationPath gomint-$Random"
```

### PowerShell
```powershell
$ProgressPreference = 'SilentlyContinue'; $Random = Get-Random -Maximum 0xFFFFFF; Invoke-WebRequest -Uri 'https://gomint-artifacts.s3.amazonaws.com/latest.zip' -OutFile gomint-$Random.zip; Expand-Archive -LiteralPath gomint-$Random.zip -DestinationPath gomint-$Random
```

### Unix (Curl + Core Utils)
```bash
curl --silent -o gomint.zip https://gomint-artifacts.s3.amazonaws.com/latest.zip
unzip gomint.zip -d gomint-$(cat /dev/urandom | base64 | cut -c 4)
```
### Unix (Wget + Core Utils)
```bash
wget --quiet -O gomint.zip https://gomint-artifacts.s3.amazonaws.com/latest.zip
unzip gomint.zip -d gomint-$(cat /dev/urandom | base64 | cut -c 4)
```

## 💠 Integration
_To be filled_

## ⚒ Compilation
**Bundled Maven**
```bash
./mvnw clean install
```

**Local Maven**
```bash
mvn clean install
```

## 👥 Contributors
### Code Contributors
This project exists thanks to all the people who contribute. [[Contribute](CONTRIBUTING.md)].
<a href="https://github.com/GoMint/GoMint/graphs/contributors"><img src="https://opencollective.com/GoMint/contributors.svg?width=890&button=false" /></a>

### Financial Contributors
Become a financial contributor and help us sustain our community. [[Contribute](https://opencollective.com/GoMint/contribute)]

#### Individuals
<a href="https://opencollective.com/GoMint"><img src="https://opencollective.com/GoMint/individuals.svg?width=890"></a>

#### Organizations
Support this project with your organization. Your logo will show up here with a link to your website. [[Contribute](https://opencollective.com/GoMint/contribute)]

<a href="https://opencollective.com/GoMint/organization/0/website"><img src="https://opencollective.com/GoMint/organization/0/avatar.svg"></a>
<a href="https://opencollective.com/GoMint/organization/1/website"><img src="https://opencollective.com/GoMint/organization/1/avatar.svg"></a>
<a href="https://opencollective.com/GoMint/organization/2/website"><img src="https://opencollective.com/GoMint/organization/2/avatar.svg"></a>
<a href="https://opencollective.com/GoMint/organization/3/website"><img src="https://opencollective.com/GoMint/organization/3/avatar.svg"></a>
<a href="https://opencollective.com/GoMint/organization/4/website"><img src="https://opencollective.com/GoMint/organization/4/avatar.svg"></a>
<a href="https://opencollective.com/GoMint/organization/5/website"><img src="https://opencollective.com/GoMint/organization/5/avatar.svg"></a>
<a href="https://opencollective.com/GoMint/organization/6/website"><img src="https://opencollective.com/GoMint/organization/6/avatar.svg"></a>
<a href="https://opencollective.com/GoMint/organization/7/website"><img src="https://opencollective.com/GoMint/organization/7/avatar.svg"></a>
<a href="https://opencollective.com/GoMint/organization/8/website"><img src="https://opencollective.com/GoMint/organization/8/avatar.svg"></a>
<a href="https://opencollective.com/GoMint/organization/9/website"><img src="https://opencollective.com/GoMint/organization/9/avatar.svg"></a>
