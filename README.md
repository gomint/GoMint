<img src=".github/ASSETS/gomint-logo-with-text.svg" width="512" alt="GoMint">

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

GoMint is a modern Minecraft Bedrock Edition server enabling you to make your visions come true

![Start Sequence](https://cdn.discordapp.com/attachments/746763011875799040/798663623547158539/sNgUe2IpW6.gif)

## ❇ Getting Started
- [🌐 Website](https://gomint.io)
- [📖 Documentation](https://docs.gomint.io)
- [🗣 Discord Server](https://discord.com/invite/qC4nJVN)

## 📦 Features
- ⚡ High performance, fast startup and low resource usage
- 🔧 Highly configurable via configuration files
- 🔌 Functionality extendable via plugins
- 🗝 Cryptography implementations in Rust
- 💫 Crafting, enchanting, all blocks, all items

## ⏬ Download
- [🟢 Latest tag](https://github.com/gomint/gomint/releases/download/1.0.0-RC6/1.0.0-RC6.zip)
- [🟡 Latest build](https://gomint-artifacts.s3.amazonaws.com/latest.zip)

⚠ **The following one-liner scripts download the latest build, not the latest tag!**

#### Windows Command Line
```bash
powershell.exe "$ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://gomint-artifacts.s3.amazonaws.com/latest.zip' -OutFile gomint.zip; $Random = Get-Random -Maximum 0xFFFFFF; Expand-Archive -LiteralPath gomint.zip -DestinationPath gomint-$Random; Write-Output gomint-$Random"
```

#### PowerShell
```powershell
$ProgressPreference = 'SilentlyContinue'; `
    Invoke-WebRequest -Uri 'https://gomint-artifacts.s3.amazonaws.com/latest.zip' -OutFile gomint.zip; `
    $Random = Get-Random -Maximum 0xFFFFFF; `
    Expand-Archive -LiteralPath gomint.zip -DestinationPath gomint-$Random; `
    Write-Output gomint-$Random
```

#### Unix (Curl + Core Utils)
```bash
WORKSPACE=gomint-$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 6 | head -n 1) && \
	curl --silent -o gomint.zip https://gomint-artifacts.s3.amazonaws.com/latest.zip && \
	unzip gomint.zip -d $WORKSPACE && echo $WORKSPACE
```

#### Unix (Wget + Core Utils)
```bash
WORKSPACE=gomint-$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 6 | head -n 1) && \
	wget --quiet -O gomint.zip https://gomint-artifacts.s3.amazonaws.com/latest.zip && \
	unzip gomint.zip -d $WORKSPACE && echo $WORKSPACE
```

## 💠 Integration
### CloudNet V3
_Guide coming soon_

### Docker
_Guide coming soon_

### Pterodactyl
_Guide coming soon_

## ⚒ Compilation
**Bundled Maven (Windows Command Line)**
```bash
mvnw clean install
```

**Bundled Maven (Shell)**
```bash
./mvnw.sh clean install
```

**Local Maven Installation**
```bash
mvn clean install
```

## 👥 Contributors
#### 📝 Code Contributors
This project exists thanks to all the people who contribute. [[Contribute](CONTRIBUTING.md)].
<a href="https://github.com/GoMint/GoMint/graphs/contributors"><img src="https://opencollective.com/GoMint/contributors.svg?width=890&button=false" /></a>

#### 💸 Financial Contributors
Become a financial contributor and help us sustain our community. [[Contribute](https://opencollective.com/GoMint/contribute)]

##### Individuals
<a href="https://opencollective.com/GoMint"><img src="https://opencollective.com/GoMint/individuals.svg?width=890"></a>

##### Organizations
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
