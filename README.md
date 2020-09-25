<p align="center">
  
![GoMint](.github/ASSETS/gomint-banner.png)

</p>

<h4 align="center">A new fresh Minecraft: Bedrock Edition server software<br>aiming for stability and performance</h4>
<p align="center">
  
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

</p>

GoMint is an open-source Minecraft Bedrock Edition server software implementation that is still work in progress. The goal is to provide a server implementation aiming for performance and stability with support for plugins. Purely written in Java.

### A word of warning
Currently, GoMint is in a fast development-mode. The API is in all corners not stable and may change over time. The goal is to develop an API with the implementation problems we face. We will break the API multiple times until we reach the first release. To keep the impact minimal we deprecate symbols and provide better alternatives you can use.

```diff
- Deprecated symbols (packages, fields, methods, classes etc.) will be deleted after two weeks of deprecation
```

## tl;dr
| JDK  | Documentation                        | Download                                                   |
| ---- | ------------------------------------ | ---------------------------------------------------------- |
| 11  | [Click here](http://docs.gomint.io) | [Latest build](https://github.com/GoMint/GoMint/actions?query=workflow%3A%22Java+CI%22) |

### Compilation
Compiling GoMint is actually pretty easy. We'll guide you through the compilation step by step and address troubleshooting.

**Prerequisites**<br>
For compiling GoMint, you will need some prerequisites:
- Git
- Maven
- JDK 11
 
**Compiling**<br>
This project's choice of build tool is Maven. To compile GoMint using Maven follow these steps:
- Open up a terminal
- Change the working directory to the cloned GoMint repository
- Type the following command: `mvn clean install` (You can append `-T 4C` if you've got a decent machine)
- Let it compile. This will take some time. Grab a drink and relax.

**Troubleshooting**<br>
_No compile troubleshooting available._

## Contributors

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

### License
This project's choice of license is **BSD 3-Clause**. You may find the license file in the project's root directory.

<br>
<p align="center">
  
  <!-- DISCORD -->
  <a href="https://discord.gg/qC4nJVN">
    <img width="32" alt="Discord Logo" src=".github/ASSETS/logo_discord.png">
  </a>
  &nbsp;
  <!-- TWITTER -->
  <a href="https://twitter.com/gomintbe">
    <img width="32" alt="Twitter Logo" src=".github/ASSETS/logo_twitter.png">
  </a>

</p>
