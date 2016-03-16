# Setup a directory to hold the patches
mkdir -p ~/tmp/patches/
# Create the patches
git format-patch -o ~/tmp/patches/ --root /path/to/copy
# Apply the patches in the new repo using a 3 way merge in case of conflicts
# (merges from the other repo are not turned into patches). 
# The 3way can be omitted.
git am --3way ~/tmp/patches/*.patch

# you can confirm the files and folders are the same by doing a simple
diff -qr $pathA $pathB

# Diff will output which path has items that differ from the other path.
# If there is no output the paths are the same.