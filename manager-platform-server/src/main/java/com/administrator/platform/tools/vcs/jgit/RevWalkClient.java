/**
 * @author : 孙留平
 * @since : 2019年2月27日 下午1:32:22
 * @see:
 */
package com.administrator.platform.tools.vcs.jgit;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : Administrator
 * @since : 2019年2月27日 下午1:32:22
 * @see :
 */
public class RevWalkClient {

    private static final Logger logger = LoggerFactory
            .getLogger(RevWalkClient.class);

    public static void test() throws IOException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Repository repository = new RepositoryBuilder()
                .setGitDir(new File("D:\\TianQue\\map-position\\.git")).build();
        try (RevWalk walk = new RevWalk(repository)) {
            Ref head = repository.findRef("HEAD");
            // 从HEAD开始遍历，
            walk.markStart(walk.parseCommit(head.getObjectId()));
            for (RevCommit commit : walk) {
                RevTree tree = commit.getTree();

                TreeWalk treeWalk = new TreeWalk(repository,
                        repository.newObjectReader());
                PathFilter f = PathFilter.create("pom.xml");
                treeWalk.setFilter(f);
                treeWalk.reset(tree);
                treeWalk.setRecursive(false);
                while (treeWalk.next()) {
                    PersonIdent authoIdent = commit.getAuthorIdent();
                    System.out.println("提交人： " + authoIdent.getName() + " <"
                            + authoIdent.getEmailAddress() + ">");
                    System.out.println("提交SHA1： " + commit.getId().name());
                    System.out.println("提交信息： " + commit.getShortMessage());
                    System.out.println(
                            "提交时间： " + format.format(authoIdent.getWhen()));

                    ObjectId objectId = treeWalk.getObjectId(0);
                    ObjectLoader loader = repository.open(objectId);
                    // 提取blob对象的内容
                    loader.copyTo(System.out);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        test();
    }
}
