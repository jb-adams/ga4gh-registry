package org.ga4gh.registry;

import com.fasterxml.jackson.databind.JsonSerializer;

import org.ga4gh.registry.exception.CustomErrorAttributes;
import org.ga4gh.registry.middleware.AuthorizationInterceptor;
import org.ga4gh.registry.model.Implementation;
import org.ga4gh.registry.model.Organization;
import org.ga4gh.registry.model.Service;
import org.ga4gh.registry.model.ServiceType;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.model.StandardVersion;
import org.ga4gh.registry.model.URIResolution;
import org.ga4gh.registry.util.auth.PlaceholderAuth;
import org.ga4gh.registry.util.hibernate.HibernateConfig;
import org.ga4gh.registry.util.hibernate.HibernateUtil;
import org.ga4gh.registry.util.requesthandler.RequestHandlerFactory;
import org.ga4gh.registry.util.requesthandler.SingleGenericRequestHandlerFactory;
import org.ga4gh.registry.util.requesthandler.delete.SingleGenericDeleteRequestHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexImplementationsHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexServiceTypesHandler;
import org.ga4gh.registry.util.requesthandler.index.IndexServicesHandler;
import org.ga4gh.registry.util.requesthandler.index.ResolveURIHandler;
import org.ga4gh.registry.util.requesthandler.index.SingleGenericIndexRequestHandler;
import org.ga4gh.registry.util.requesthandler.post.PostServiceHandler;
import org.ga4gh.registry.util.requesthandler.post.SingleGenericPostRequestHandler;
import org.ga4gh.registry.util.requesthandler.put.PutServiceHandler;
import org.ga4gh.registry.util.requesthandler.put.SingleGenericPutRequestHandler;
import org.ga4gh.registry.util.requesthandler.utils.ImplementationRequestUtils;
import org.ga4gh.registry.util.requesthandler.show.ShowServiceInfoHandler;
import org.ga4gh.registry.util.requesthandler.show.SingleGenericShowRequestHandler;
import org.ga4gh.registry.util.hibernate.HibernateQuerier;
import org.ga4gh.registry.util.hibernate.HibernateQueryBuilder;
import org.ga4gh.registry.util.serialize.RegistrySerializerModule;
import org.ga4gh.registry.util.serialize.RegistrySerializerModuleHelper;
import org.ga4gh.registry.util.serialize.serializers.DateSerializer;
import org.ga4gh.registry.util.serialize.serializers.ImplementationSerializer;
import org.ga4gh.registry.util.serialize.serializers.OrganizationSerializer;
import org.ga4gh.registry.util.serialize.serializers.RegistryErrorSerializer;
import org.ga4gh.registry.util.serialize.serializers.ReleaseStatusSerializer;
import org.ga4gh.registry.util.serialize.serializers.ServiceSerializer;
import org.ga4gh.registry.util.serialize.serializers.ServiceTypeSerializer;
import org.ga4gh.registry.util.serialize.serializers.StandardCategorySerializer;
import org.ga4gh.registry.util.serialize.serializers.StandardSerializer;
import org.ga4gh.registry.util.serialize.serializers.StandardVersionSerializer;
import org.ga4gh.registry.util.serialize.serializers.WorkStreamSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConfigurationProperties
public class AppConfig implements WebMvcConfigurer {

    /* ******************************
     * LOW-LEVEL HIBERNATE-RELATED BEANS
     * ****************************** */

    @Bean
    public HibernateConfig getHibernateConfig() {
        return new HibernateConfig();
    }

    @Bean
    public HibernateUtil getHibernateUtil() {
        return new HibernateUtil();
    }

    @Bean
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQueryBuilder getHibernateQueryBuilder() {
        return new HibernateQueryBuilder();
    }

    /* ******************************
     * HIBERNATE QUERIER BEANS
     * ****************************** */

    @Bean
    @Qualifier(AppConfigConstants.STANDARD_HIBERNATE_QUERIER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQuerier<Standard> getStandardsHibernateQuerier() {
        return new HibernateQuerier<>(Standard.class);
    }

    @Bean
    @Qualifier(AppConfigConstants.STANDARD_VERSION_HIBERNATE_QUERIER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQuerier<StandardVersion> getStandardVersionsHibernateQuerier() {
        return new HibernateQuerier<>(StandardVersion.class);
    }

    @Bean
    @Qualifier(AppConfigConstants.ORGANIZATION_HIBERNATE_QUERIER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQuerier<Organization> getOrganizationsHibernateQuerier() {
        return new HibernateQuerier<>(Organization.class);
    }

    @Bean
    @Qualifier(AppConfigConstants.IMPLEMENTATION_HIBERNATE_QUERIER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQuerier<Implementation> getImplementationsHibernateQuerier() {
        return new HibernateQuerier<>(Implementation.class);
    }

    @Bean
    @Qualifier(AppConfigConstants.SERVICE_HIBERNATE_QUERIER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public HibernateQuerier<Service> getServicesHibernateQuerier() {
        return new HibernateQuerier<>(Service.class);
    }

    /* ******************************
     * REQUEST HANDLER BEANS
     * ****************************** */

    /* STANDARD REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.INDEX_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericIndexRequestHandler<Standard> standardIndexRequestHandler(
        @Qualifier (AppConfigConstants.BASIC_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier (AppConfigConstants.STANDARD_HIBERNATE_QUERIER) HibernateQuerier<Standard> querier
    ) {
        return new SingleGenericIndexRequestHandler<>(Standard.class, serializerModule, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericShowRequestHandler<Standard> standardShowRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericShowRequestHandler<>(Standard.class, serializerModule, AppConfigConstants.STANDARD_ID);
    }

    @Bean(name = AppConfigConstants.POST_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericPostRequestHandler<Standard> standardPostRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericPostRequestHandler<>(Standard.class, serializerModule);
    }

    @Bean(name = AppConfigConstants.PUT_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericPutRequestHandler<Standard> standardPutRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericPutRequestHandler<>(Standard.class, serializerModule, AppConfigConstants.STANDARD_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_STANDARD_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericDeleteRequestHandler<Standard> standardDeleteRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericDeleteRequestHandler<>(Standard.class, serializerModule, AppConfigConstants.STANDARD_ID);
    }

    /* ORGANIZATION REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.INDEX_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericIndexRequestHandler<Organization> organizationIndexRequestHandler(
        @Qualifier (AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier (AppConfigConstants.ORGANIZATION_HIBERNATE_QUERIER) HibernateQuerier<Organization> querier
    ) {
        return new SingleGenericIndexRequestHandler<>(Organization.class, serializerModule, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericShowRequestHandler<Organization> organizationShowRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericShowRequestHandler<>(Organization.class, serializerModule, AppConfigConstants.ORGANIZATION_ID);
    }

    @Bean(name = AppConfigConstants.POST_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericPostRequestHandler<Organization> organizationPostRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericPostRequestHandler<>(Organization.class, serializerModule);
    }

    @Bean(name = AppConfigConstants.PUT_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericPutRequestHandler<Organization> organizationPutRequestHandler(
        @Qualifier (AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericPutRequestHandler<>(Organization.class, serializerModule, AppConfigConstants.ORGANIZATION_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_ORGANIZATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericDeleteRequestHandler<Organization> organizationDeleteRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericDeleteRequestHandler<>(Organization.class, serializerModule, AppConfigConstants.ORGANIZATION_ID);
    }

    /* IMPLEMENTATION REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.INDEX_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexImplementationsHandler implementationIndexRequestHandler(
        @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier(AppConfigConstants.IMPLEMENTATION_HIBERNATE_QUERIER) HibernateQuerier<Implementation> querier
    ) {
        return new IndexImplementationsHandler(Implementation.class, serializerModule, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericShowRequestHandler<Implementation> implementationShowRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericShowRequestHandler<>(Implementation.class, serializerModule, AppConfigConstants.IMPLEMENTATION_ID);
    }

    @Bean(name = AppConfigConstants.POST_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericPostRequestHandler<Implementation> implementationPostRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericPostRequestHandler<>(Implementation.class, serializerModule);
    }

    @Bean(name = AppConfigConstants.PUT_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericPutRequestHandler<Implementation> implementationPutRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericPutRequestHandler<>(Implementation.class, serializerModule, AppConfigConstants.IMPLEMENTATION_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_IMPLEMENTATION_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericDeleteRequestHandler<Implementation> implementationDeleteRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericDeleteRequestHandler<>(Implementation.class, serializerModule, AppConfigConstants.IMPLEMENTATION_ID);
    }

    /* SERVICE REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.INDEX_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexServicesHandler serviceIndexRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier(AppConfigConstants.SERVICE_HIBERNATE_QUERIER) HibernateQuerier<Service> querier
    ) {
        return new IndexServicesHandler(Service.class, serializerModule, querier);
    }

    @Bean(name = AppConfigConstants.SHOW_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericShowRequestHandler<Service> serviceShowRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericShowRequestHandler<>(Service.class, serializerModule, AppConfigConstants.SERVICE_ID);
    }

    @Bean(name = AppConfigConstants.POST_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PostServiceHandler servicePostRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PostServiceHandler(Service.class, serializerModule);
    }

    @Bean(name = AppConfigConstants.PUT_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public PutServiceHandler servicePutRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new PutServiceHandler(Service.class, serializerModule, AppConfigConstants.SERVICE_ID);
    }

    @Bean(name = AppConfigConstants.DELETE_SERVICE_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public SingleGenericDeleteRequestHandler<Service> serviceDeleteRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new SingleGenericDeleteRequestHandler<>(Service.class, serializerModule, AppConfigConstants.SERVICE_ID);
    }

    @Bean(name = AppConfigConstants.INDEX_SERVICE_TYPES_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public IndexServiceTypesHandler serviceTypesHandler(
        @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER_MODULE) RegistrySerializerModule serializerModule,
        @Qualifier(AppConfigConstants.SERVICE_HIBERNATE_QUERIER) HibernateQuerier<Service> querier
    ) {
        return new IndexServiceTypesHandler(Service.class, ServiceType.class, serializerModule, querier);
    }

    /* SERVICE-INFO REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.SHOW_SERVICE_INFO_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ShowServiceInfoHandler showServiceInfoRequestHandler(
        @Qualifier(AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER_MODULE) RegistrySerializerModule serializerModule
    ) {
        return new ShowServiceInfoHandler(Service.class, serializerModule, AppConfigConstants.SERVICE_ID);
    }

    /* RESOLVE REQUEST HANDLER BEANS */

    @Bean(name = AppConfigConstants.RESOLVE_URI_HANDLER)
    @Scope(AppConfigConstants.PROTOTYPE)
    public ResolveURIHandler resolveURIHandler(
        // SERIALIZER MODULE GOES HERE
        @Qualifier(AppConfigConstants.IMPLEMENTATION_HIBERNATE_QUERIER) HibernateQuerier<Service> querier
    ) {
        return new ResolveURIHandler(Service.class, null, querier);
    }

    /* ******************************
     * REQUEST HANDLER FACTORY BEANS
     * ****************************** */

    /* STANDARD REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.INDEX_STANDARD_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Standard> indexStandardHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Standard.class, AppConfigConstants.INDEX_STANDARD_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.SHOW_STANDARD_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Standard> showStandardHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Standard.class, AppConfigConstants.SHOW_STANDARD_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.POST_STANDARD_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Standard> postStandardHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Standard.class, AppConfigConstants.POST_STANDARD_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.PUT_STANDARD_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Standard> putStandardHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Standard.class, AppConfigConstants.PUT_STANDARD_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.DELETE_STANDARD_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Standard> deleteStandardHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Standard.class, AppConfigConstants.DELETE_STANDARD_HANDLER);
    }

    /* ORGANIZATION REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.INDEX_ORGANIZATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Organization> indexOrganizationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Organization.class, AppConfigConstants.INDEX_ORGANIZATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.SHOW_ORGANIZATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Organization> showOrganizationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Organization.class, AppConfigConstants.SHOW_ORGANIZATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.POST_ORGANIZATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Organization> postOrganizationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Organization.class, AppConfigConstants.POST_ORGANIZATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.PUT_ORGANIZATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Organization> putOrganizationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Organization.class, AppConfigConstants.PUT_ORGANIZATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.DELETE_ORGANIZATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Organization> deleteOrganizationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Organization.class, AppConfigConstants.DELETE_ORGANIZATION_HANDLER);
    }

    /* IMPLEMENTATION REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.INDEX_IMPLEMENTATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Implementation> indexImplementationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Implementation.class, AppConfigConstants.INDEX_IMPLEMENTATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.SHOW_IMPLEMENTATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Implementation> showImplementationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Implementation.class, AppConfigConstants.SHOW_IMPLEMENTATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.POST_IMPLEMENTATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Implementation> postImplementationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Implementation.class, AppConfigConstants.POST_IMPLEMENTATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.PUT_IMPLEMENTATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Implementation> putImplementationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Implementation.class, AppConfigConstants.PUT_IMPLEMENTATION_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.DELETE_IMPLEMENTATION_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Implementation> deleteImplementationHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Implementation.class, AppConfigConstants.DELETE_IMPLEMENTATION_HANDLER);
    }

    /* SERVICE REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.INDEX_SERVICE_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Service> indexServiceHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Service.class, AppConfigConstants.INDEX_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.SHOW_SERVICE_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Service> showServiceHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Service.class, AppConfigConstants.SHOW_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.POST_SERVICE_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Service> postServiceHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Service.class, AppConfigConstants.POST_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.PUT_SERVICE_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Service> putServiceHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Service.class, AppConfigConstants.PUT_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.DELETE_SERVICE_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Service> deleteServiceHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Service.class, AppConfigConstants.DELETE_SERVICE_HANDLER);
    }

    @Bean
    @Qualifier(AppConfigConstants.INDEX_SERVICE_TYPES_HANDLER_FACTORY)
    public RequestHandlerFactory<Service, Service, ServiceType> indexServiceTypesHandlerFactory() {
        return new RequestHandlerFactory<>(Service.class, ServiceType.class, AppConfigConstants.INDEX_SERVICE_TYPES_HANDLER);
    }

    /* SERVICE-INFO REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.SHOW_SERVICE_INFO_HANDLER_FACTORY)
    public SingleGenericRequestHandlerFactory<Service> showServiceInfoHandlerFactory() {
        return new SingleGenericRequestHandlerFactory<>(Service.class, AppConfigConstants.SHOW_SERVICE_INFO_HANDLER);
    }

    /* RESOLVE REQUEST HANDLER FACTORY BEANS */

    @Bean
    @Qualifier(AppConfigConstants.RESOLVE_URI_HANDLER_FACTORY)
    public RequestHandlerFactory<Implementation, Implementation, URIResolution> resolveURIHandlerFactory() {
        return new RequestHandlerFactory<>(Implementation.class, URIResolution.class, AppConfigConstants.RESOLVE_URI_HANDLER);
    }

    /* ******************************
     * SERIALIZER BEANS
     * ****************************** */

    /* STANDARD SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_STANDARD_SERIALIZER)
    public StandardSerializer basicStandardSerializer() {
        return new StandardSerializer();
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER)
    public StandardSerializer relationalStandardSerializer() {
        String[] relationalAttributes = {"description", "versions", "workStream"};
        return new StandardSerializer(relationalAttributes);
    }

    /* STANDARD CATEGORY SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_STANDARD_CATEGORY_SERIALIZER)
    public StandardCategorySerializer basicStandardCategorySerializer() {
        return new StandardCategorySerializer();
    }

    /* RELEASE STATUS SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_RELEASE_STATUS_SERIALIZER)
    public ReleaseStatusSerializer basicReleaseStatusSerializer() {
        return new ReleaseStatusSerializer();
    }

    /* STANDARD VERSION SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_STANDARD_VERSION_SERIALIZER)
    public StandardVersionSerializer basicStandardVersionSerializer() {
        return new StandardVersionSerializer();
    }

    /* ORGANIZATION SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER)
    public OrganizationSerializer basicOrganizationSerializer() {
        return new OrganizationSerializer();
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER)
    public OrganizationSerializer relationalOrganizationSerializer() {
        String[] relationalAttributes = {"implementations"};
        return new OrganizationSerializer(relationalAttributes);
    }

    /* IMPLEMENTATION SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER)
    public ImplementationSerializer basicImplementationSerializer() {
        String[] relationalAttributes = {"organization"};
        return new ImplementationSerializer(relationalAttributes);
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER)
    public ImplementationSerializer relationalImplementationSerializer() {
        String[] relationalAttributes = {"organization", "standards"};
        return new ImplementationSerializer(relationalAttributes);
    }

    /* SERVICE SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_SERVICE_SERIALIZER)
    public ServiceSerializer basicServiceSerializer() {
        return new ServiceSerializer();
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER)
    public ServiceSerializer relationalServiceSerializer() {
        String []relationalAttributes = {"organization"};
        return new ServiceSerializer(relationalAttributes);
    }

    /* WORK STREAM SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_WORK_STREAM_SERIALIZER)
    public WorkStreamSerializer basicWorkStreamSerializer() {
        return new WorkStreamSerializer();
    }

    /* REGISTRY ERROR SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER)
    public RegistryErrorSerializer basicRegistryErrorSerializer() {
        return new RegistryErrorSerializer();
    }

    /* SERVICE TYPE SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER)
    public ServiceTypeSerializer basicServiceTypeSerializer() {
        return new ServiceTypeSerializer();
    }

    /* DATE SERIALIZER BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_DATE_SERIALIZER)
    public DateSerializer basicDateSerializer() {
        return new DateSerializer();
    }

    /* ******************************
     * SERIALIZER MODULE BEANS
     * ****************************** */

    /* STANDARD SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_STANDARD_SERIALIZER_MODULE)
    public RegistrySerializerModule basicStandardSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_STANDARD_SERIALIZER) StandardSerializer standardSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_CATEGORY_SERIALIZER) StandardCategorySerializer standardCategorySerializer,
        @Qualifier(AppConfigConstants.BASIC_RELEASE_STATUS_SERIALIZER) ReleaseStatusSerializer releaseStatusSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_STANDARD_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("standard"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {standardSerializer, standardCategorySerializer, releaseStatusSerializer})
        );
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalStandardSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER) StandardSerializer standardSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_VERSION_SERIALIZER) StandardVersionSerializer standardVersionSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_CATEGORY_SERIALIZER) StandardCategorySerializer standardCategorySerializer,
        @Qualifier(AppConfigConstants.BASIC_RELEASE_STATUS_SERIALIZER) ReleaseStatusSerializer releaseStatusSerializer,
        @Qualifier(AppConfigConstants.BASIC_WORK_STREAM_SERIALIZER) WorkStreamSerializer workStreamSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.RELATIONAL_STANDARD_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("standard"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {standardSerializer, standardVersionSerializer, standardCategorySerializer, releaseStatusSerializer, workStreamSerializer})
        );
    }

    /* ORGANIZATION SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER_MODULE)
    public RegistrySerializerModule basicOrganizationSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("organization"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {organizationSerializer})
        );
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalOrganizationSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer,
        @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER) ImplementationSerializer implementationSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.RELATIONAL_ORGANIZATION_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("organization"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {organizationSerializer, implementationSerializer})
        );
    }

    /* IMPLEMENTATION SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER_MODULE)
    public RegistrySerializerModule basicImplementationSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER) ImplementationSerializer implementationSerializer,
        @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_IMPLEMENTATION_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("implementation"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {implementationSerializer, organizationSerializer})
        );
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalImplementationSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER) ImplementationSerializer implementationSerializer,
        @Qualifier(AppConfigConstants.BASIC_DATE_SERIALIZER) DateSerializer dateSerializer,
        @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_SERIALIZER) StandardSerializer standardSerializer,
        @Qualifier(AppConfigConstants.BASIC_STANDARD_CATEGORY_SERIALIZER) StandardCategorySerializer standardCategorySerializer,
        @Qualifier(AppConfigConstants.BASIC_RELEASE_STATUS_SERIALIZER) ReleaseStatusSerializer releaseStatusSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.RELATIONAL_IMPLEMENTATION_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("implementation"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {
                implementationSerializer,
                dateSerializer,
                organizationSerializer,
                standardSerializer,
                standardCategorySerializer,
                releaseStatusSerializer}
            )
        );
    }

    /* SERVICE SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_SERVICE_SERIALIZER_MODULE)
    public RegistrySerializerModule basicServiceSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_SERVICE_SERIALIZER) ServiceSerializer serviceSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_SERVICE_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("service"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {serviceSerializer})
        );
    }

    @Bean
    @Qualifier(AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER_MODULE)
    public RegistrySerializerModule relationalServiceSerializerModule(
        @Qualifier(AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER) ServiceSerializer serviceSerializer,
        @Qualifier(AppConfigConstants.BASIC_DATE_SERIALIZER) DateSerializer dateSerializer,
        @Qualifier(AppConfigConstants.BASIC_ORGANIZATION_SERIALIZER) OrganizationSerializer organizationSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.RELATIONAL_SERVICE_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("service"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {serviceSerializer, dateSerializer, organizationSerializer})
        );
    }

    /* REGISTRY ERROR SERIALIZER MODULE BEANS */

    @Bean
    @Qualifier(AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER_MODULE)
    public RegistrySerializerModule basicRegistryErrorSerializerModule(
        @Qualifier(AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER) RegistryErrorSerializer registryErrorSerializer,
        @Qualifier(AppConfigConstants.BASIC_DATE_SERIALIZER) DateSerializer dateSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_REGISTRY_ERROR_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("registryError"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {registryErrorSerializer, dateSerializer})
        );
    }

    /* SERVICE TYPE SERIALIZER MODULE BEANS */
    
    @Bean
    @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER_MODULE)
    public RegistrySerializerModule basicServiceTypeSerializierModule(
        @Qualifier(AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER) ServiceTypeSerializer serviceTypeSerializer
    ) {
        return new RegistrySerializerModule(
            AppConfigConstants.BASIC_SERVICE_TYPE_SERIALIZER_MODULE,
            RegistrySerializerModuleHelper.newVersion("serviceType"),
            RegistrySerializerModuleHelper.newSerializers(new JsonSerializer<?>[] {serviceTypeSerializer})
        );
    }

    /* ******************************
     * OTHER UTILS BEANS
     * ****************************** */

    @Bean
    @Scope(AppConfigConstants.PROTOTYPE)
    public ImplementationRequestUtils getServiceRequestUtils() {
        return new ImplementationRequestUtils();
    }

    @Bean
    public CustomErrorAttributes customErrorAttributes() {
        return new CustomErrorAttributes();
    }

    /* ******************************
     * MIDDLEWARE BEANS
     * ****************************** */

    @Bean
    public PlaceholderAuth getPlaceholderAuth() {
        return new PlaceholderAuth();
    }

    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor());
    }
}