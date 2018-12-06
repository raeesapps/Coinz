package net.raeesaamir.coinz.game;

import com.google.gson.Gson;

/**
 * Contains utility methods that will be used for testing the feature system.
 *
 * @author raeesaamir
 */
class TestUtils {

    /**
     * The GSON instance. Converts JSON strings into objects.
     */
    private static final Gson gson = new Gson();

    /**
     * Builds a feature from a JSON string.
     *
     * @return The feature.
     */
    public static Feature getExampleFeature() {
        String feature = "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"c58a-9e18-1285-6a70-c44b-f4f2\",\n" +
                "        \"value\": \"3.639937895762324\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"3\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.190015783400452,\n" +
                "          55.94457042402356\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    }";
        return gson.fromJson(feature, Feature.class);

    }

    /**
     * Builds a feature collection object from a JSON string.
     *
     * @return The feature collection object.
     */
    public static FeatureCollection getExampleFeatureCollection() {

        String featureCollection = "{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"date-generated\": \"Mon Jan 01 2018\",\n" +
                "  \"time-generated\": \"00:00\",\n" +
                "  \"approximate-time-remaining\": \"23:59\",\n" +
                "  \"rates\": {\n" +
                "                   \"SHIL\": 7.0,\n" +
                "                   \"DOLR\": 9.0,\n" +
                "                   \"QUID\": 11.0,\n" +
                "                   \"PENY\": 13.0\n" +
                "               },\n" +
                "  \"features\": [\n" +
                "  \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"700d-b6f2-da88-a5dc-6002-cabe\",\n" +
                "        \"value\": \"9.54859807530815\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"9\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.187067851289144,\n" +
                "          55.94583163791626\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"084a-142b-cd0e-c13d-ea95-cf0b\",\n" +
                "        \"value\": \"8.857426051162557\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"8\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.187740700366516,\n" +
                "          55.94569852523585\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"3d93-a328-2b30-f574-7774-15ca\",\n" +
                "        \"value\": \"6.08755467742556\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"6\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1880864078274236,\n" +
                "          55.94312037731119\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"c72a-eaaf-ba19-170e-7b03-974b\",\n" +
                "        \"value\": \"0.5901685593209194\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"0\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1868301923640945,\n" +
                "          55.943010536823124\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"5a09-a4de-bd0a-98aa-1891-68e6\",\n" +
                "        \"value\": \"4.637902073475969\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"4\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.187412052546838,\n" +
                "          55.9458392015966\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"3be4-6ec8-728f-01c0-1c76-3156\",\n" +
                "        \"value\": \"4.504042669740328\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"4\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1920692692309576,\n" +
                "          55.94345615342773\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"e97c-cf81-353b-1780-f6dc-1913\",\n" +
                "        \"value\": \"3.1596871075784163\",\n" +
                "        \"currency\": \"QUID\",\n" +
                "        \"marker-symbol\": \"3\",\n" +
                "        \"marker-color\": \"#ffdf00\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1864827000440195,\n" +
                "          55.9429233986976\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"1e8d-fc72-908b-f6b8-a2b4-b59d\",\n" +
                "        \"value\": \"9.199822133595244\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"9\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1888280379032667,\n" +
                "          55.94418531614446\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"ba20-4c90-f603-6bb3-62e3-7cd1\",\n" +
                "        \"value\": \"9.149551671596182\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"9\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1893479994845815,\n" +
                "          55.94557713280665\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"b470-d0ff-b3cd-941d-a55f-f0a4\",\n" +
                "        \"value\": \"0.7699496310603005\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"0\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1892239836368876,\n" +
                "          55.94464624535436\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"c123-baef-5908-12d1-9980-0b46\",\n" +
                "        \"value\": \"0.8058260025171504\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"0\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1843591939124387,\n" +
                "          55.94431763305385\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"6082-5544-722f-9c6e-d055-e8be\",\n" +
                "        \"value\": \"9.478979134990047\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"9\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1852278415466055,\n" +
                "          55.94597622596988\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"6379-38cd-7830-d8de-e089-00af\",\n" +
                "        \"value\": \"4.324394843951783\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"4\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.18891986593543,\n" +
                "          55.945371035610975\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"6576-7546-2603-5b15-b0d9-703f\",\n" +
                "        \"value\": \"3.5318209370445786\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"3\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.185283258332288,\n" +
                "          55.94571898547116\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"d310-9b0c-d3ff-022e-9fad-6894\",\n" +
                "        \"value\": \"7.602770577669739\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"7\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.185829482665269,\n" +
                "          55.94610000854362\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"c84b-6084-620c-9798-963e-46ce\",\n" +
                "        \"value\": \"4.539296874164929\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"4\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1893803187184475,\n" +
                "          55.94394805676217\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"77da-ed6a-a546-a1a6-c9f1-cf8b\",\n" +
                "        \"value\": \"1.6212840440957277\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"1\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.190636062964664,\n" +
                "          55.945757959707365\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"60c6-cb14-b6c6-c9aa-998c-cfa5\",\n" +
                "        \"value\": \"8.828040727600573\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"8\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1868044158588393,\n" +
                "          55.943471479745895\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"67c7-b2f6-d904-2525-84d6-3c41\",\n" +
                "        \"value\": \"9.816105562235057\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"9\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.185305534205249,\n" +
                "          55.943025783944805\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"c148-f755-717d-7919-c90d-c78e\",\n" +
                "        \"value\": \"8.659105953339441\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"8\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1922785376657026,\n" +
                "          55.94347391903619\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"283a-6be3-a330-82bc-55cb-dd64\",\n" +
                "        \"value\": \"7.479496071014566\",\n" +
                "        \"currency\": \"QUID\",\n" +
                "        \"marker-symbol\": \"7\",\n" +
                "        \"marker-color\": \"#ffdf00\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.188885956246091,\n" +
                "          55.944026277232496\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"7ce3-7f3f-4ccd-ad79-d1fa-f9b3\",\n" +
                "        \"value\": \"8.175375486834815\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"8\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1917376208017885,\n" +
                "          55.94325141385302\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"1a8f-aff9-ffcd-22a4-49f4-ff44\",\n" +
                "        \"value\": \"3.7611416981039216\",\n" +
                "        \"currency\": \"QUID\",\n" +
                "        \"marker-symbol\": \"3\",\n" +
                "        \"marker-color\": \"#ffdf00\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.19152394940818,\n" +
                "          55.94313236681344\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"f0cf-3fc3-a89e-55b9-28ec-4134\",\n" +
                "        \"value\": \"1.3990895344584964\",\n" +
                "        \"currency\": \"QUID\",\n" +
                "        \"marker-symbol\": \"1\",\n" +
                "        \"marker-color\": \"#ffdf00\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.186163294905956,\n" +
                "          55.94455503365558\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"1f1f-d076-4f69-0f6c-4bcb-52b5\",\n" +
                "        \"value\": \"0.12557721662497245\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"0\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1843390586955853,\n" +
                "          55.94504421330549\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"18f2-d17a-47ff-949c-d3af-e952\",\n" +
                "        \"value\": \"9.073048924096272\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"9\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1861992337525473,\n" +
                "          55.94338483246231\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"e74b-80f2-789f-faa5-c32e-fedc\",\n" +
                "        \"value\": \"9.14386516883087\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"9\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.19010606379182,\n" +
                "          55.94269757510665\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"f3d2-cb4a-f805-6e6f-3df4-39eb\",\n" +
                "        \"value\": \"9.205969756682912\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"9\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.185659601779166,\n" +
                "          55.94599240657455\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"2eed-0749-8e34-1bd4-a33b-e399\",\n" +
                "        \"value\": \"5.686266239922368\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"5\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.188067669386906,\n" +
                "          55.944570522583064\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"aba3-b7a8-fe76-327a-1fb9-a796\",\n" +
                "        \"value\": \"4.140343923799847\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"4\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.191765003707775,\n" +
                "          55.946216906188134\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"c58a-9e18-1285-6a70-c44b-f4f2\",\n" +
                "        \"value\": \"3.639937895762324\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"3\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.190015783400452,\n" +
                "          55.94457042402356\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"a40f-ca4e-55c5-0d82-3c83-e5ff\",\n" +
                "        \"value\": \"6.600313108702402\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"6\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.19099368642823,\n" +
                "          55.94565026141016\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"a712-0546-e40d-7b21-1d0f-20e1\",\n" +
                "        \"value\": \"7.177209617370678\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"7\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1889613272998836,\n" +
                "          55.94328756878153\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"0667-c6a1-c484-e523-e002-9a1a\",\n" +
                "        \"value\": \"0.5753977728045878\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"0\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1851626445958434,\n" +
                "          55.94445703611517\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"8243-3adf-e5fd-b6b1-88e4-7c2b\",\n" +
                "        \"value\": \"1.7548401110000245\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"1\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1921605796802983,\n" +
                "          55.94418573015321\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"f184-5f5d-64e0-28c7-7d4e-f1e3\",\n" +
                "        \"value\": \"4.146369815331877\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"4\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.191734184388565,\n" +
                "          55.94358569581415\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"b1f6-8039-cf40-743a-b7fc-9904\",\n" +
                "        \"value\": \"4.390653885934027\",\n" +
                "        \"currency\": \"QUID\",\n" +
                "        \"marker-symbol\": \"4\",\n" +
                "        \"marker-color\": \"#ffdf00\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1859121257335494,\n" +
                "          55.94442738969619\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"468a-1cdc-bd60-7e9f-c7b5-a2b0\",\n" +
                "        \"value\": \"2.076876112112812\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"2\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1867077486277458,\n" +
                "          55.94510346516062\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"e82a-d153-81ff-6fab-3161-ce72\",\n" +
                "        \"value\": \"3.7714291449636015\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"3\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.190305266350912,\n" +
                "          55.94536129729214\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"bc7b-76ed-1813-1f71-ea84-7e47\",\n" +
                "        \"value\": \"8.902748155237909\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"8\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.186972520503338,\n" +
                "          55.943023169183306\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"285a-53c5-995a-2eb6-d54f-578a\",\n" +
                "        \"value\": \"8.584978376878754\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"8\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1849576748025896,\n" +
                "          55.94285352946584\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"21ec-ec2c-afda-6d91-e4ef-3ede\",\n" +
                "        \"value\": \"2.3721678692101635\",\n" +
                "        \"currency\": \"DOLR\",\n" +
                "        \"marker-symbol\": \"2\",\n" +
                "        \"marker-color\": \"#008000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1923340297007137,\n" +
                "          55.946079743196876\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"2e7b-3525-1649-0aee-7bdb-9822\",\n" +
                "        \"value\": \"5.178887428545846\",\n" +
                "        \"currency\": \"QUID\",\n" +
                "        \"marker-symbol\": \"5\",\n" +
                "        \"marker-color\": \"#ffdf00\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.19054302476788,\n" +
                "          55.94541292868023\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"43a6-d711-4419-607b-b711-e3a6\",\n" +
                "        \"value\": \"5.652936258564004\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"5\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.187425843233253,\n" +
                "          55.94388473485991\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"3200-f10f-19db-3fe0-c056-9025\",\n" +
                "        \"value\": \"6.15569254953526\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"6\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1920134912096736,\n" +
                "          55.943403399647565\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"53de-81bc-1247-57a8-4463-62d4\",\n" +
                "        \"value\": \"2.801112851901607\",\n" +
                "        \"currency\": \"PENY\",\n" +
                "        \"marker-symbol\": \"2\",\n" +
                "        \"marker-color\": \"#ff0000\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.185714162090657,\n" +
                "          55.94612655455123\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"67bc-14e9-1b7d-72d7-96c0-2d44\",\n" +
                "        \"value\": \"0.5170801384479551\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"0\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.189747707777688,\n" +
                "          55.94365070444994\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"9278-b522-ad49-cfe1-b134-d82f\",\n" +
                "        \"value\": \"1.8946231169305616\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"1\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1882129645816524,\n" +
                "          55.944471621420135\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"370e-7ffa-761a-ae51-9443-efce\",\n" +
                "        \"value\": \"7.3123881319087545\",\n" +
                "        \"currency\": \"SHIL\",\n" +
                "        \"marker-symbol\": \"7\",\n" +
                "        \"marker-color\": \"#0000ff\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.184354257428158,\n" +
                "          55.942910973137096\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    },\n" +
                "        \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \n" +
                "      \"properties\": {\n" +
                "        \"id\": \"d5ec-6bda-5d1b-b0fb-29d3-6227\",\n" +
                "        \"value\": \"3.0376040050463295\",\n" +
                "        \"currency\": \"QUID\",\n" +
                "        \"marker-symbol\": \"3\",\n" +
                "        \"marker-color\": \"#ffdf00\"\n" +
                "      },\n" +
                "      \n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          -3.1917626609685144,\n" +
                "          55.9441728737721\n" +
                "        ]\n" +
                "      }\n" +
                "\n" +
                "    }\n" +
                "        \n" +
                "   ]\n" +
                "}";
        return gson.fromJson(featureCollection, FeatureCollection.class);
    }

}
