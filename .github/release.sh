#
# Copyright (c) 2020 Gomint team
#
# This code is licensed under the BSD license found in the
# LICENSE file in the root directory of this source tree.
#

#!/bin/bash
sha256sum $1.zip > $1.zip.sha256
echo "{}" > $1.json
sha256sum $1.json > $1.json.sha256
